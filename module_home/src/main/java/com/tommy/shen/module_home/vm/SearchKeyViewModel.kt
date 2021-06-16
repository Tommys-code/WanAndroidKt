package com.tommy.shen.module_home.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tommy.shen.module_common.base.BaseViewModel
import com.tommy.shen.module_home.data.SearchKeyData
import com.tommy.shen.module_home.repository.HomeRepository
import kotlinx.coroutines.launch

class SearchKeyViewModel : BaseViewModel<HomeRepository>() {

    private val hotKeyData = MutableLiveData<List<SearchKeyData>>()
    private val historyKeyData = MutableLiveData<List<String>>()

    override fun getRepository() = HomeRepository()

    fun getHotKey(): LiveData<List<SearchKeyData>> {
        return hotKeyData.launch { repo.getHotKey() }
    }

    fun getHistoryKey(): LiveData<List<String>> {
        viewModelScope.launch {
            historyKeyData.postValue(repo.getHistoryKey())
        }
        return historyKeyData
    }

    fun saveHistoryKey(key: String) {
        if (key.isEmpty()) return
        viewModelScope.launch {
            val data = (historyKeyData.value ?: emptyList()).toMutableList()
            data.apply {
                remove(key)
                add(0, key)
            }.take(10)
            repo.saveHistoryKey(data)
            historyKeyData.postValue(data)
        }
    }

    fun deleteHistory() {
        viewModelScope.launch {
            repo.deleteHistoryKey()
            historyKeyData.postValue(null)
        }
    }

}