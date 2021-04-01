package com.tommy.shen.module_project.net

import com.tommy.shen.module_common.data.BaseListData
import com.tommy.shen.module_common.data.BaseResult
import com.tommy.shen.module_common.http.NetWork
import com.tommy.shen.module_project.data.ArticleData
import com.tommy.shen.module_project.data.ProjectTreeData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

object Instance {

    val api by lazy { NetWork.createApi<ProjectService>() }

}

interface ProjectService {

    /**
     * 项目分类
     * https://www.wanandroid.com/project/tree/json
     */
    @GET("project/tree/json")
    suspend fun getProjectTree(): BaseResult<List<ProjectTreeData>>

    @GET("project/list/{pageNum}/json")
    suspend fun getProjectList(
        @Path("pageNum") pageNum: Int,
        @Query("cid") id: Int
    ): BaseResult<BaseListData<ArticleData>>

}