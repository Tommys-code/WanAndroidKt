package com.tommy.shen.module_public.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.tommy.shen.module_common.base.BaseFragment
import com.tommy.shen.module_common.constant.Public
import com.tommy.shen.module_common.util.ToastUtils
import com.tommy.shen.module_common.util.init
import com.tommy.shen.module_public.R
import com.tommy.shen.module_public.adapter.PublicArticleAdapter
import com.tommy.shen.module_public.databinding.FragPublicBinding
import com.tommy.shen.module_public.view.ChooseChapterDialog
import com.tommy.shen.module_public.vm.PublicViewModel
import kotlinx.coroutines.flow.collectLatest

@Route(path = Public.PUBLIC_PAGE)
class PublicFragment : BaseFragment<FragPublicBinding>() {

    private val viewModel by viewModels<PublicViewModel>()
    private val adapter by lazy { PublicArticleAdapter() }
    private val dialog by lazy {
        ChooseChapterDialog(requireContext()).apply {
            setOnItemClickListener {
                viewModel.id = it.id
                binding.name.text = it.name.firstOrNull().toString()
                adapter.refresh()
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.frag_public

    override fun onCreate() {
        binding.titleBar.init(R.string.public_title)

        binding.refresh.init { adapter.refresh() }
        binding.recycle.adapter = adapter

        viewModel.getWxArticle().observe(this, Observer {
            dialog.setData(it)
            it.firstOrNull()?.run {
                binding.name.text = name.firstOrNull().toString()
                viewModel.id = id

                lifecycleScope.launchWhenCreated {
                    viewModel.listData.collectLatest  { data -> adapter.submitData(data) }
                }
            }
        })
        adapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.Loading -> binding.refresh.isRefreshing = true
                is LoadState.NotLoading -> binding.refresh.isRefreshing = false
                is LoadState.Error -> binding.refresh.isRefreshing = false
            }
        }

        binding.name.setOnClickListener {
            dialog.show()
        }
        adapter.setOnCollectedListener { collect, id, position ->
            viewModel.collectArticle(id, collect, position)
        }
        viewModel.collectLiveData.observe(this, Observer {
            val isCollect = adapter.setItemCollected(it)
            ToastUtils.showSnackBar(
                binding.root,
                if (isCollect) R.string.home_collect_success else R.string.home_un_collect_success
            )
        })
    }

}