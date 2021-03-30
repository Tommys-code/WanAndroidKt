package com.tommy.shen.module_home.net

import com.tommy.shen.module_common.data.BaseListData
import com.tommy.shen.module_common.data.BaseResult
import com.tommy.shen.module_home.data.ArticleData
import com.tommy.shen.module_home.data.BannerData
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeService {

    /**
     * 首页文章列表
     * https://www.wanandroid.com/article/list/0/json
     */
    @GET("article/list/{pageNum}/json")
    suspend fun getArticleList(@Path("pageNum") pageNum: Int): BaseResult<BaseListData<ArticleData>>

    /**
     * 首页banner
     * https://www.wanandroid.com/banner/json
     */
    @GET("banner/json")
    suspend fun getBanner(): BaseResult<List<BannerData>>
}