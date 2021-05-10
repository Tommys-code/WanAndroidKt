package com.tommy.shen.module_my.act

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.tommy.shen.module_common.base.BaseActivity
import com.tommy.shen.module_common.constant.Mine
import com.tommy.shen.module_common.constant.NEED_LOGIN
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

    override fun getLayoutId(): Int = R.layout.my_score_act

    override fun onCreate() {
        binding.toolbar.init(getString(R.string.my_score))

        binding.recycle.adapter = adapter

        lifecycleScope.launch {
            viewModel.listData.collect { adapter.submitData(it) }
        }
    }

}