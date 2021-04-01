package com.tommy.shen.module_project.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tommy.shen.module_common.base.BaseViewModel
import com.tommy.shen.module_project.data.ProjectTreeData
import com.tommy.shen.module_project.repo.ProjectRepository

class ProjectViewModel : BaseViewModel<ProjectRepository>() {

    val tabLiveData = MutableLiveData<List<ProjectTreeData>>()

    override fun getRepository() = ProjectRepository()

    fun getTab(): LiveData<List<ProjectTreeData>> {
        return tabLiveData.launch { repo.getProjectTree() }
    }


}