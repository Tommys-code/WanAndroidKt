package com.tommy.shen.module_project.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.tabs.TabLayoutMediator
import com.tommy.shen.module_common.base.BaseFragment
import com.tommy.shen.module_common.constant.Project
import com.tommy.shen.module_project.R
import com.tommy.shen.module_project.databinding.FragProjectBinding
import com.tommy.shen.module_project.vm.ProjectViewModel


@Route(path = Project.PROJECT_PAGE)
class ProjectFragment : BaseFragment<FragProjectBinding>() {

    private val viewModel by viewModels<ProjectViewModel>()

    private val frags = ArrayList<Fragment>()
    private val adapter by lazy {

        object : FragmentStateAdapter(requireActivity()) {
            override fun getItemCount(): Int = frags.size

            override fun createFragment(position: Int): Fragment {
                return frags[position]
            }

        }
    }

    override fun getLayoutId(): Int = R.layout.frag_project

    override fun onCreate() {
        binding.titleBar.init(R.string.project_title)
        binding.tabContent.adapter = adapter
        TabLayoutMediator(binding.tabLay, binding.tabContent, true,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = viewModel.tabLiveData.value?.get(position)?.name
            }).attach()
        viewModel.getTab().observe(this, Observer {
            it.forEach { data ->
                frags.add(
                    ARouter.getInstance().build(Project.PROJECT_LIST_PAGE).withInt("id", data.id)
                        .navigation() as Fragment
                )
            }
            adapter.notifyDataSetChanged()
        })
    }


}