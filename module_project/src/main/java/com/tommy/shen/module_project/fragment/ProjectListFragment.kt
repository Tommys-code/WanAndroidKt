package com.tommy.shen.module_project.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.tommy.shen.module_common.base.BaseFragment
import com.tommy.shen.module_common.constant.Project
import com.tommy.shen.module_common.util.init
import com.tommy.shen.module_project.R
import com.tommy.shen.module_project.adapter.ProjectListAdapter
import com.tommy.shen.module_project.databinding.FragProjectListBinding
import com.tommy.shen.module_project.vm.ProjectListViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Route(path = Project.PROJECT_LIST_PAGE)
class ProjectListFragment : BaseFragment<FragProjectListBinding>() {

    @JvmField
    @Autowired(name = "id")
    var projectId: Int = 0

    private val viewModel by viewModels<ProjectListViewModel>()
    private val adapter by lazy { ProjectListAdapter() }

    override fun getLayoutId(): Int = R.layout.frag_project_list

    override fun onCreate() {
        ARouter.getInstance().inject(this)
        viewModel.id = projectId

        binding.recycle.adapter = adapter
        binding.refresh.init { adapter.refresh() }

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


}