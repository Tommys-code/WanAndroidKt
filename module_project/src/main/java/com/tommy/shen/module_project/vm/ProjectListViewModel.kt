package com.tommy.shen.module_project.vm

import com.tommy.shen.module_common.base.BasePagingViewModel
import com.tommy.shen.module_common.data.BaseListData
import com.tommy.shen.module_project.data.ArticleData
import com.tommy.shen.module_project.repo.ProjectRepository

class ProjectListViewModel : BasePagingViewModel<ProjectRepository, ArticleData>() {

    var id: Int = 0

    override fun getRepository() = ProjectRepository()

    override suspend fun getData(pageSize: Int): BaseListData<ArticleData>? {
        return repo.getProjectList(pageSize, id)
    }

}