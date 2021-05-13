package com.tommy.shen.module_my.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tommy.shen.module_common.base.BasePagingViewModel
import com.tommy.shen.module_common.base.BaseRepository
import com.tommy.shen.module_common.base.BaseViewModel
import com.tommy.shen.module_common.data.BaseListData
import com.tommy.shen.module_common.util.ToastUtils
import com.tommy.shen.module_my.data.CoinData
import com.tommy.shen.module_my.data.CoinListData
import com.tommy.shen.module_my.repo.MineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoinViewModel : BasePagingViewModel<MineRepository, CoinListData>() {

    override val pageIndex: Int
        get() = 1

    val coinLiveData = MutableLiveData<CoinData>()

    override fun getRepository() = MineRepository()

    fun getCoin(): MutableLiveData<CoinData> =
        coinLiveData.launchWithCache(isShowLoading = true) { repo.getCoin() }

    override suspend fun getData(pageNum: Int): BaseListData<CoinListData>? =
        repo.getCoinList(pageNum)

}