package com.tommy.shen.module_main.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.launcher.ARouter
import com.tommy.shen.module_common.constant.Home

class MainPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    private val frags = listOf(
        (ARouter.getInstance().build(Home.HOME_PAGE).navigation() as Fragment?),
        (ARouter.getInstance().build(Home.HOME_PAGE).navigation() as Fragment?),
        (ARouter.getInstance().build(Home.HOME_PAGE).navigation() as Fragment?),
        (ARouter.getInstance().build(Home.HOME_PAGE).navigation() as Fragment?)
    )

    override fun getItemCount(): Int = frags.size

    override fun createFragment(position: Int): Fragment {
        return frags[position] ?: Fragment()
    }

}