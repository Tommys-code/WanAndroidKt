package com.tommy.shen.module_my.repo

import com.tommy.shen.module_common.base.BaseRepository

class MineRepository :BaseRepository(){

    private val api = Instance.api

    suspend fun getCoin() = request { api.getCoin() }

}