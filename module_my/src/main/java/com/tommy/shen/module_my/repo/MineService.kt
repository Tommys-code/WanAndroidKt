package com.tommy.shen.module_my.repo

import com.tommy.shen.module_common.data.BaseResult
import com.tommy.shen.module_common.http.NetWork
import com.tommy.shen.module_my.data.CoinData
import retrofit2.http.GET

interface MineService {

    @GET("lg/coin/userinfo/json")
    suspend fun getCoin(): BaseResult<CoinData>

}

object Instance {

    val api by lazy { NetWork.createApi<MineService>() }

}