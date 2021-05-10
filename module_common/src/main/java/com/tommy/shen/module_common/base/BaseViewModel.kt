package com.tommy.shen.module_common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tommy.shen.module_common.util.ToastUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class BaseViewModel<T : BaseRepository> : ViewModel() {

    val isShowLoading = MutableLiveData<Boolean>()
    val repo: T by lazy { getRepository() }

    abstract fun getRepository(): T

    protected fun <DATA> MutableLiveData<DATA>.launch(
        isShowLoading: Boolean = false,
        isShowError: Boolean = true,
        onError: ((code: Int) -> Unit)? = null,
        onFinally: (() -> Unit)? = null,
        request: suspend () -> DATA
    ): MutableLiveData<DATA> {
        if (isShowLoading) showLoading()
        viewModelScope.launch {
            try {
                postValue(request.invoke())
            } catch (e: Exception) {
                when (e) {
                    is BaseRepository.MyException -> {
                        if (isShowError) ToastUtils.showToast(e.msg)
                        onError?.invoke(e.errorCode)
                    }
                }
            } finally {
                dismissLoading()
                onFinally?.invoke()
            }
        }
        return this
    }

    protected fun <DATA> MutableLiveData<DATA>.launchWithCache(
        isShowLoading: Boolean = false,
        isShowError: Boolean = true,
        onError: ((code: Int) -> Unit)? = null,
        onFinally: (() -> Unit)? = null,
        request: suspend () -> Flow<DATA>
    ): MutableLiveData<DATA> {
        if (isShowLoading) showLoading()
        viewModelScope.launch {
            try {
                request.invoke().collect { postValue(it) }
            } catch (e: Exception) {
                when (e) {
                    is BaseRepository.MyException -> {
                        if (isShowError) ToastUtils.showToast(e.msg)
                        onError?.invoke(e.errorCode)
                    }
                }
            } finally {
                dismissLoading()
                onFinally?.invoke()
            }
        }
        return this
    }

    private fun showLoading() {
        isShowLoading.postValue(true)
    }

    private fun dismissLoading() {
        isShowLoading.postValue(false)
    }

}