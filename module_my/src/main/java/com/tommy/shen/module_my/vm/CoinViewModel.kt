package com.tommy.shen.module_my.vm

import androidx.lifecycle.MutableLiveData
import com.tommy.shen.module_common.base.BaseViewModel
import com.tommy.shen.module_my.data.CoinData
import com.tommy.shen.module_my.repo.MineRepository

class CoinViewModel : BaseViewModel<MineRepository>() {

    val coinLiveData = MutableLiveData<CoinData>()

    override fun getRepository() = MineRepository()

    fun getCoin(): MutableLiveData<CoinData> =
        coinLiveData.launch(isShowLoading = true) { repo.getCoin() }

}