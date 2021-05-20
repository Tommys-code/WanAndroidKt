package com.tommy.shen.module_my.act

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.tommy.shen.module_common.base.BaseActivity
import com.tommy.shen.module_common.base.LoadMoreAdapter
import com.tommy.shen.module_common.constant.Mine
import com.tommy.shen.module_common.constant.NEED_LOGIN
import com.tommy.shen.module_common.util.init
import com.tommy.shen.module_my.R
import com.tommy.shen.module_my.adapter.ArticleAdapter
import com.tommy.shen.module_my.databinding.ActMyCollectBinding
import com.tommy.shen.module_my.vm.CollectViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Route(path = Mine.MY_COLLECT, extras = NEED_LOGIN)
class MyCollectAct : BaseActivity<ActMyCollectBinding>() {

    private val viewModel by viewModels<CollectViewModel>()
    private val adapter by lazy { ArticleAdapter() }

    override fun getLayoutId(): Int = R.layout.act_my_collect

    override fun onCreate() {
        binding.toolbar.init(getString(R.string.my_collect))

        binding.refresh.init { adapter.refresh() }
        binding.empty.error.setOnClickListener { adapter.retry() }

        binding.recycle.isFocusableInTouchMode = false
        binding.recycle.adapter = adapter.withLoadStateFooter(LoadMoreAdapter { adapter.retry() })

        lifecycleScope.launch {
            viewModel.listData.collectLatest { adapter.submitData(it) }
        }
        adapter.addLoadStateListener { loadState ->
            loadState.decideOnState(showLoading = {
                binding.refresh.isRefreshing = it
            }, showEmptyState = {
                binding.type = if (it) 1 else binding.type
            }, showError = {
                binding.type = if (it) 2 else binding.type
            })
        }
        adapter.setOnCollectedListener { id, position -> viewModel.unCollectArticle(id, position) }
        viewModel.unCollectLiveData.observe(this, Observer { adapter.refresh() })
    }

    private inline fun CombinedLoadStates.decideOnState(
        showLoading: (Boolean) -> Unit,
        showEmptyState: (Boolean) -> Unit,
        showError: (Boolean) -> Unit
    ) {
        showLoading(refresh is LoadState.Loading)
        showEmptyState(
            refresh is LoadState.NotLoading &&
                    append is LoadState.NotLoading
                    && append.endOfPaginationReached
                    && adapter.itemCount == 0
        )
        showError(
            source.refresh is LoadState.Error
        )
    }
}