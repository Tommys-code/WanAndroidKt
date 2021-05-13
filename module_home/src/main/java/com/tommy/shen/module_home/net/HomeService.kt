package com.tommy.shen.module_home.net

import com.tommy.shen.module_common.data.BaseListData
import com.tommy.shen.module_common.data.BaseResult
import com.tommy.shen.module_home.data.ArticleData
import com.tommy.shen.module_home.data.BannerData
import com.tommy.shen.module_home.data.SearchKeyData
import retrofit2.http.*

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

    /**
     * 搜索热词
     * https://www.wanandroid.com/hotkey/json
     */
    @GET("hotkey/json")
    suspend fun getHotKey(): BaseResult<List<SearchKeyData>>

    /**
     *
     */
    @POST("article/query/{pageNum}/json")
    suspend fun queryArticle(
        @Path("pageNum") pageNum: Int,
        @Query("k") k: String
    ): BaseResult<BaseListData<ArticleData>>

    @POST("lg/collect/{id}/json")
    suspend fun collect(@Path("id") id: Int): BaseResult<Any?>

    @POST("lg/uncollect_originId/{id}/json")
    suspend fun unCollectArticle(@Path("id") id: Int): BaseResult<Any?>
}