package com.tommy.shen.module_login.vm

import androidx.lifecycle.MutableLiveData
import com.tommy.shen.module_common.base.BaseViewModel
import com.tommy.shen.module_login.data.User
import com.tommy.shen.module_login.repo.LoginRepo

class LoginViewModel : BaseViewModel<LoginRepo>() {

    val registerAndLoginLiveData = MutableLiveData<User>()

    override fun getRepository() = LoginRepo()

    fun register(username: String, password: String, rePassword: String): MutableLiveData<User> =
        registerAndLoginLiveData.launch(isShowLoading = true) {
            repo.register(username, password, rePassword)
        }

    fun login(username: String, password: String): MutableLiveData<User> =
        registerAndLoginLiveData.launch(isShowLoading = true) {
            repo.login(username, password)
        }
}