package com.tommy.shen.module_common.base

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tommy.shen.module_common.app.BaseApplication
import com.tommy.shen.module_common.data.BaseResult
import com.tommy.shen.module_common.http.NetWork
import com.tommy.shen.module_common.util.LoginRouterInterceptor
import com.tommy.shen.module_common.util.ToastUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

open class BaseRepository {

    val db by lazy { BaseApplication.db }

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

    suspend inline fun <reified T> request(
        cacheKey: String? = null,
        crossinline call: suspend () -> BaseResult<T>
    ): Flow<T> {
        return flow {
            try {
                cacheKey?.let { getData<T>(it)?.let { value -> emit(value) } }
                call.invoke().apply {
                    //这儿可以对返回结果errorCode做一些特殊处理，比如token失效等，可以通过抛出异常的方式实现
                    when (errorCode) {
                        //0请求成功，直接返回数据
                        0 -> {
                            cacheKey?.let { saveData(cacheKey, data) }
                            return@apply
                        }
                        //登录失效
                        -1001 -> {
                            throw (MyException(errorCode, errorMsg))
                        }
                        else -> {
                            throw (MyException(errorCode, errorMsg))
                        }
                    }
                }.data.let {
                    emit(it)
                }
            } catch (e: Exception) {
                ToastUtils.showToast("网络异常")
                throw (Exception(e.message))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend inline fun <reified T> saveData(key: String, value: T) {
        val data = NetWork.getMoShi().adapter(T::class.java)
            .toJson(value)
        db.myDao().save(key, data)
    }

    suspend inline fun <reified T> getData(key: String): T? {
        return db.myDao().get(key)?.let {
            NetWork.getMoShi().adapter(T::class.java)
                .fromJson(it.data_value)
        }
    }

    class MyException(val errorCode: Int, val msg: String) : Exception(msg)
}