package com.tommy.shen.module_project.repo

import com.tommy.shen.module_common.base.BaseRepository
import com.tommy.shen.module_project.net.Instance

class ProjectRepository : BaseRepository() {

    private val api = Instance.api

    suspend fun getProjectTree() = request { api.getProjectTree() }

    suspend fun getProjectList(pageNum: Int, id: Int) = request { api.getProjectList(pageNum, id) }

    suspend fun collectArticle(id: Int) = request { api.collect(id) }

    suspend fun unCollectArticle(id: Int) = request { api.unCollectArticle(id) }

}