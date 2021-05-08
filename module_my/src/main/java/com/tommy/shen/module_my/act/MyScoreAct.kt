package com.tommy.shen.module_my.act

import com.alibaba.android.arouter.facade.annotation.Route
import com.tommy.shen.module_common.base.BaseActivity
import com.tommy.shen.module_common.constant.Mine
import com.tommy.shen.module_common.constant.NEED_LOGIN
import com.tommy.shen.module_my.R
import com.tommy.shen.module_my.databinding.MyScoreActBinding

@Route(path = Mine.MY_SCORE, extras = NEED_LOGIN)
class MyScoreAct : BaseActivity<MyScoreActBinding>() {

    override fun getLayoutId(): Int = R.layout.my_score_act

    override fun onCreate() {
        binding.toolbar.init(getString(R.string.my_score))
    }

}