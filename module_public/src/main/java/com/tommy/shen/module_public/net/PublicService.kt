package com.tommy.shen.module_public.net

import com.tommy.shen.module_common.data.BaseListData
import com.tommy.shen.module_common.data.BaseResult
import com.tommy.shen.module_common.http.NetWork
import com.tommy.shen.module_public.data.ArticleData
import com.tommy.shen.module_public.data.WxArticleData
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

object Instance {

    val api by lazy { NetWork.createApi<PublicService>() }

}


interface PublicService {

    /**
     * 获取公众号列表
     * https://wanandroid.com/wxarticle/chapters/json
     */
    @GET("wxarticle/chapters/json")
    suspend fun getWxArticle(): BaseResult<List<WxArticleData>>

    @GET("wxarticle/list/{id}/{pageNum}/json")
    suspend fun getArticleById(
        @Path("id") id: Int,
        @Path("pageNum") pageNum: Int
    ): BaseResult<BaseListData<ArticleData>>

    @POST("lg/collect/{id}/json")
    suspend fun collect(@Path("id") id: Int): BaseResult<Any?>

    @POST("lg/uncollect_originId/{id}/json")
    suspend fun unCollectArticle(@Path("id") id: Int): BaseResult<Any?>
}