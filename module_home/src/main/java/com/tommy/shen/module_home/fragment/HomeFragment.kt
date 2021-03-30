package com.tommy.shen.module_home.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.tommy.shen.module_common.base.BaseFragment
import com.tommy.shen.module_common.constant.Home
import com.tommy.shen.module_common.util.init
import com.tommy.shen.module_common.util.openWeb
import com.tommy.shen.module_home.R
import com.tommy.shen.module_home.adapter.HomeArticleAdapter
import com.tommy.shen.module_home.data.BannerData
import com.tommy.shen.module_home.databinding.FragHomeBinding
import com.tommy.shen.module_home.vm.HomeViewModel
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.RectangleIndicator
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Route(path = Home.HOME_PAGE)
class HomeFragment : BaseFragment<FragHomeBinding>() {

    private val viewModel by viewModels<HomeViewModel>()
    private val adapter by lazy { HomeArticleAdapter() }

    override fun getLayoutId(): Int = R.layout.frag_home

    override fun onCreate() {
        binding.titleBar.init("首页")
        binding.refresh.init { loadData() }
        binding.articleRecycle.adapter = adapter

        viewModel.getBanner().observe(viewLifecycleOwner, Observer {
            initBanner(it)
        })
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

    private fun loadData() {
        viewModel.getBanner()
        adapter.refresh()
    }

    private fun initBanner(list: List<BannerData>?) {
        binding.banner.apply {
            adapter = object : BannerImageAdapter<BannerData>(list) {
                override fun onBindView(
                    holder: BannerImageHolder, data: BannerData?, position: Int, size: Int
                ) {
                    Glide.with(holder.itemView).load(data?.imagePath).into(holder.imageView)
                }
            }.apply {
                setOnBannerListener { data, _ ->
                    openWeb(
                        this@HomeFragment.requireContext(),
                        (data as BannerData).url,
                        data.title,
                        data.id
                    )
                }
            }
            addBannerLifecycleObserver(this@HomeFragment)
            setLoopTime(6000)
            indicator = RectangleIndicator(requireContext())
            setIndicatorHeight(resources.getDimensionPixelOffset(R.dimen.indicatorSize))
            setIndicatorNormalWidth(resources.getDimensionPixelOffset(R.dimen.indicatorSize))
            setIndicatorSelectedWidth(resources.getDimensionPixelOffset(R.dimen.dp14))
            setIndicatorRadius(resources.getDimensionPixelOffset(R.dimen.indicatorSize))
        }
    }
}