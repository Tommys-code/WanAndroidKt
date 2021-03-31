package com.tommy.shen.module_home.act

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.tommy.shen.module_common.base.BaseActivity
import com.tommy.shen.module_common.constant.Home
import com.tommy.shen.module_common.util.init
import com.tommy.shen.module_home.R
import com.tommy.shen.module_home.adapter.HomeArticleAdapter
import com.tommy.shen.module_home.databinding.ActSearchResultBinding
import com.tommy.shen.module_home.vm.SearchResultViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Route(path = Home.SEARCH_RESULT_PAGE)
class SearchResultAct : BaseActivity<ActSearchResultBinding>() {

    @JvmField
    @Autowired
    var key: String = ""

    private val viewModel by viewModels<SearchResultViewModel>()
    private val adapter by lazy { HomeArticleAdapter() }

    override fun getLayoutId(): Int = R.layout.act_search_result

    override fun onCreate() {
        ARouter.getInstance().inject(this)
        binding.cancel.setOnClickListener { finish() }
        binding.searchKey.text = key

        viewModel.queryKey = key

        binding.refresh.init { adapter.refresh() }
        binding.recycle.adapter = adapter

        lifecycleScope.launch {
            viewModel.listData.collect { adapter.submitData(it) }
        }
        adapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.Loading -> binding.refresh.isRefreshing = true
                is LoadState.NotLoading -> binding.refresh.isRefreshing = false
                is LoadState.Error -> binding.refresh.isRefreshing = false
            }
        }
    }

}