package com.tommy.shen.module_common.base

import com.tommy.shen.module_common.app.BaseApplication
import com.tommy.shen.module_common.data.BaseResult
import com.tommy.shen.module_common.util.ToastUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class BaseRepository {

    protected val db by lazy { BaseApplication.db }

    suspend fun <T : Any> request(call: suspend () -> BaseResult<T>): T {
        return try {
            withContext(Dispatchers.IO) { call.invoke() }.apply {
                //这儿可以对返回结果errorCode做一些特殊处理，比如token失效等，可以通过抛出异常的方式实现
                when (errorCode) {
                    //0请求成功，直接返回数据
                    0 -> return@apply
                    //登录失效
                    -1001 -> {
                        throw (MyException(errorCode, errorMsg))
                    }
                    else -> {
                        throw (MyException(errorCode, errorMsg))
                    }
                }
            }.data
        } catch (e: Exception) {
            ToastUtils.showToast("网络异常")
            throw (Exception(e.message))
        }
    }

    class MyException(val errorCode: Int, val msg: String) : Exception(msg)
}