package com.tommy.shen.module_login.repo

import com.tommy.shen.module_common.base.BaseRepository
import com.tommy.shen.module_login.net.Instance

class LoginRepo : BaseRepository() {

    private val api = Instance.api

    suspend fun register(username: String, password: String, rePassword: String) =
        request { api.register(username, password, rePassword) }

    suspend fun login(username: String, password: String) =
        request { api.login(username, password) }

}