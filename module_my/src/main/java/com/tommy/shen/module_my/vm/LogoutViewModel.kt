package com.tommy.shen.module_my.vm

import androidx.lifecycle.MutableLiveData
import com.tommy.shen.module_common.base.BaseViewModel
import com.tommy.shen.module_my.repo.MineRepository

class LogoutViewModel : BaseViewModel<MineRepository>() {

    override fun getRepository() = MineRepository()

    val logoutLiveData = MutableLiveData<Any>()

    fun logout() = logoutLiveData.launch { repo.logout() }

}