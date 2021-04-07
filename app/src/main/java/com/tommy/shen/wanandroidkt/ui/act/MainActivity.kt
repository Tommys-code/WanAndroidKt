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
                R.id.homeFragment -> changeTab(0)
                R.id.projectFragment -> changeTab(1)
                R.id.squareFragment -> changeTab(2)
                R.id.myFragment -> changeTab(3)
            }
            true
        }
    }

    private fun changeTab(item: Int) {
        binding.viewPager.setCurrentItem(item, false)
    }
}