package com.tommy.shen.module_common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tommy.shen.module_common.scope.netScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

abstract class BaseViewModel<T : BaseRepository> : ViewModel() {

    val isShowLoading by lazy { MutableLiveData<Boolean>() }
    val repo: T by lazy { getRepository() }

    abstract fun getRepository(): T

    protected fun <DATA> MutableLiveData<DATA>.launch(
        isShowLoading: Boolean = false,
        isShowError: Boolean = true,
        error: ((code: Int) -> Unit)? = null,//失败特殊处理
        complete: (() -> Unit)? = null,
        request: suspend () -> DATA?
    ): MutableLiveData<DATA> {
        if (isShowLoading) showLoading()
        netScope(isShowError, error, complete = { finally(isShowLoading, complete) }) {
            postValue(request.invoke())
        }
        return this
    }

    protected fun <DATA> MutableLiveData<DATA>.launchWithCache(
        isShowLoading: Boolean = false,
        isShowError: Boolean = true,
        onError: ((code: Int) -> Unit)? = null,
        complete: (() -> Unit)? = null,
        request: suspend () -> Flow<DATA>
    ): MutableLiveData<DATA> {
        if (isShowLoading) showLoading()
        netScope(isShowError, onError, complete = { finally(isShowLoading, complete) }) {
            request.invoke().collect { postValue(it) }
        }
        return this
    }

    private fun finally(isShowLoading: Boolean, onFinally: (() -> Unit)? = null) {
        if (isShowLoading) dismissLoading()
        onFinally?.invoke()
    }

    private fun showLoading() {
        isShowLoading.postValue(true)
    }

    private fun dismissLoading() {
        isShowLoading.postValue(false)
    }

}