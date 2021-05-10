package com.tommy.shen.module_my.repo

import com.tommy.shen.module_common.data.BaseListData
import com.tommy.shen.module_common.data.BaseResult
import com.tommy.shen.module_common.http.NetWork
import com.tommy.shen.module_my.data.CoinData
import com.tommy.shen.module_my.data.CoinListData
import retrofit2.http.GET
import retrofit2.http.Path

interface MineService {

    @GET("lg/coin/userinfo/json")
    suspend fun getCoin(): BaseResult<CoinData>

    @GET("lg/coin/list/{pageNum}/json")
    suspend fun getCoinList(@Path("pageNum") pageNum: Int): BaseResult<BaseListData<CoinListData>>

}

object Instance {

    val api by lazy { NetWork.createApi<MineService>() }

}