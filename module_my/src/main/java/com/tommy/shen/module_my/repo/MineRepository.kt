package com.tommy.shen.module_my.repo

import com.tommy.shen.module_common.base.BaseRepository

class MineRepository : BaseRepository() {

    private val api = Instance.api

    suspend fun getCoin() = request("COIN") { api.getCoin() }

    suspend fun getCoinList(pageNum: Int) = request { api.getCoinList(pageNum) }

    suspend fun getCollectList(pageNum: Int) = request { api.getCollectList(pageNum) }

    suspend fun unCollectArticle(id: Int) = request { api.unCollectArticle(id) }

}