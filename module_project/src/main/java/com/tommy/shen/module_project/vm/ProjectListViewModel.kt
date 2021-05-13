package com.tommy.shen.module_project.vm

import androidx.lifecycle.MutableLiveData
import com.tommy.shen.module_common.base.BasePagingViewModel
import com.tommy.shen.module_common.data.BaseListData
import com.tommy.shen.module_project.data.ArticleData
import com.tommy.shen.module_project.repo.ProjectRepository

class ProjectListViewModel : BasePagingViewModel<ProjectRepository, ArticleData>() {

    var id: Int = 0
    val collectLiveData = MutableLiveData<Int>()

    override fun getRepository() = ProjectRepository()

    override suspend fun getData(pageNum: Int): BaseListData<ArticleData>? {
        return repo.getProjectList(pageNum, id)
    }

    fun collectArticle(id: Int, collect: Boolean, position: Int) =
        collectLiveData.launch {
            if (collect) {
                repo.unCollectArticle(id)
                position
            } else {
                repo.collectArticle(id)
                position
            }
        }

}