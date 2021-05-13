package com.tommy.shen.module_public.repo

import com.tommy.shen.module_common.base.BaseRepository
import com.tommy.shen.module_public.net.Instance

class PublicRepository : BaseRepository() {

    private val api = Instance.api

    suspend fun getWxArticle() = request { api.getWxArticle() }

    suspend fun getArticleById(id: Int, pageSize: Int) =
        request { api.getArticleById(id, pageSize) }

    suspend fun collectArticle(id: Int) = request { api.collect(id) }

    suspend fun unCollectArticle(id: Int) = request { api.unCollectArticle(id) }
}