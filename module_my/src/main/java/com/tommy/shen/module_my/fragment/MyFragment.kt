package com.tommy.shen.module_my.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.tommy.shen.module_common.base.BaseFragment
import com.tommy.shen.module_common.constant.Login
import com.tommy.shen.module_common.constant.Mine
import com.tommy.shen.module_my.R
import com.tommy.shen.module_my.databinding.FragMyBinding

@Route(path = Mine.MINE_PAGE)
class MyFragment : BaseFragment<FragMyBinding>() {

    override fun getLayoutId(): Int = R.layout.frag_my

    override fun onCreate() {
        binding.test.setOnClickListener { Login.openRegister() }
    }
}