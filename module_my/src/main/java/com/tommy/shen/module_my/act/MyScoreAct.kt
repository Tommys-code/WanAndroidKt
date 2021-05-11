package com.tommy.shen.module_my.act

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.tommy.shen.module_common.base.BaseActivity
import com.tommy.shen.module_common.constant.Mine
import com.tommy.shen.module_common.constant.NEED_LOGIN
import com.tommy.shen.module_common.util.init
import com.tommy.shen.module_my.R
import com.tommy.shen.module_my.adapter.ScoreHeadAdapter
import com.tommy.shen.module_my.adapter.ScoreListAdapter
import com.tommy.shen.module_my.databinding.MyScoreActBinding
import com.tommy.shen.module_my.vm.CoinViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Route(path = Mine.MY_SCORE, extras = NEED_LOGIN)
class MyScoreAct : BaseActivity<MyScoreActBinding>() {

    private val viewModel by viewModels<CoinViewModel>()
    private val adapter by lazy { ScoreListAdapter() }
    private val headAdapter by lazy { ScoreHeadAdapter() }

    override fun getLayoutId(): Int = R.layout.my_score_act

    override fun onCreate() {
        binding.toolbar.init(getString(R.string.my_score))

        binding.refresh.init { adapter.refresh() }
        binding.recycle.adapter = addHeadAdapter()

        lifecycleScope.launch {
            viewModel.listData.collect { adapter.submitData(it) }
        }
        adapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.Loading -> binding.refresh.isRefreshing = true
                else -> binding.refresh.isRefreshing = false
            }
        }
        viewModel.getCoin().observe(this, Observer { headAdapter.setScore(it.coinCount) })
    }

    private fun addHeadAdapter(): ConcatAdapter {
        return ConcatAdapter(headAdapter, adapter.addFooter())
    }

}