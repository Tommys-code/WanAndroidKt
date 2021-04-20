package com.tommy.shen.module_login.net

import com.tommy.shen.module_common.data.BaseResult
import com.tommy.shen.module_common.http.NetWork
import com.tommy.shen.module_login.data.User
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginAndRegisterService {

    @POST("user/register")
    suspend fun register(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("repassword") repassword: String
    ): BaseResult<User>

    @POST("user/login")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): BaseResult<User>

}


object Instance {

    val api by lazy { NetWork.createApi<LoginAndRegisterService>() }

}