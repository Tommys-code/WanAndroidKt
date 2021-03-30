package com.tommy.shen.module_home.repository

import com.tommy.shen.module_common.base.BaseRepository
import com.tommy.shen.module_home.data.BannerData
import com.tommy.shen.module_home.net.createApi


class HomeRepository : BaseRepository() {

    private val api = createApi()

    suspend fun getBannerInfo(): List<BannerData> = request { api.getBanner() }

    suspend fun getArticleList(pageNum: Int) = request { api.getArticleList(pageNum) }

}