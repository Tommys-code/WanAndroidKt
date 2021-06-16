package com.tommy.shen.module_common.scope

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tommy.shen.module_common.base.BaseRepository
import com.tommy.shen.module_common.http.NetErrorException
import com.tommy.shen.module_common.util.ToastUtils
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

private fun errorToastHandler(onError: ((code: Int) -> Unit)? = null) =
    CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is NetErrorException -> {
                onError?.invoke(throwable.errorCode)
                ToastUtils.showToast(throwable.msg)
            }
            else -> ToastUtils.showToast("网络异常")
        }
    }


fun ViewModel.netScope(
    isShowError: Boolean = true,
    onError: ((code: Int) -> Unit)? = null,
    complete: (() -> Unit)? = null,
    block: suspend () -> Unit
) =
    (if (isShowError) viewModelScope.launch(errorToastHandler(onError)) {
        block.invoke()
    } else viewModelScope.launch {
        block.invoke()
    }).invokeOnCompletion {
        complete?.invoke()
    }
