package com.tommy.shen.wanandroidkt.ui.act

import com.alibaba.android.arouter.facade.annotation.Route
import com.tommy.shen.module_common.base.BaseActivity
import com.tommy.shen.module_common.constant.Main
import com.tommy.shen.wanandroidkt.R
import com.tommy.shen.wanandroidkt.databinding.ActivityMainBinding
import com.tommy.shen.wanandroidkt.ui.adapter.MainPagerAdapter

@Route(path = Main.MAIN_PAGE)
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val mainAdapter by lazy { MainPagerAdapter(this) }

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun onCreate() {
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.adapter = mainAdapter
        binding.bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> binding.viewPager.currentItem = 0
                R.id.projectFragment -> binding.viewPager.currentItem = 1
                R.id.squareFragment -> binding.viewPager.currentItem = 2
                R.id.myFragment -> binding.viewPager.currentItem = 3
            }
            true
        }
    }
}