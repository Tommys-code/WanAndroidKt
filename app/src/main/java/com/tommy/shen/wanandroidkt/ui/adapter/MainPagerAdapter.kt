package com.tommy.shen.wanandroidkt.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.launcher.ARouter
import com.tommy.shen.module_common.constant.Home
import com.tommy.shen.module_common.constant.Mine
import com.tommy.shen.module_common.constant.Project
import com.tommy.shen.module_common.constant.Public

class MainPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    private val frags = listOf(
        (ARouter.getInstance().build(Home.HOME_PAGE).navigation() as Fragment?),
        (ARouter.getInstance().build(Project.PROJECT_PAGE).navigation() as Fragment?),
        (ARouter.getInstance().build(Public.PUBLIC_PAGE).navigation() as Fragment?),
        (ARouter.getInstance().build(Mine.MINE_PAGE).navigation() as Fragment?)
    )

    override fun getItemCount(): Int = frags.size

    override fun createFragment(position: Int): Fragment {
        return frags[position] ?: Fragment()
    }

}