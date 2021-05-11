package com.tommy.shen.module_common.http

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tommy.shen.module_common.http.cookie.LoginInterceptor
import com.tommy.shen.module_common.http.cookie.SaveCookieInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object NetWork {

    const val BASE_URL = "https://www.wanandroid.com"

    //retrofit对象
    private var retrofit: Retrofit? = null

//    val api: ApiService by lazy { getRetrofit().create(ApiService::class.java) }

    fun getRetrofit(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getHttpClient())
                .addConverterFactory(MoshiConverterFactory.create(getMoShi()))
                .build()
        }
        return retrofit!!
    }

    fun getMoShi() = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private fun getHttpClient(): OkHttpClient {
        //日志拦截器
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .retryOnConnectionFailure(true)//失败重试一次
            .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时
            .readTimeout(10, TimeUnit.SECONDS)//读取超时
            .writeTimeout(10, TimeUnit.SECONDS)//写入超时
            .addInterceptor(SaveCookieInterceptor())
            .addInterceptor(LoginInterceptor())
            .addInterceptor(interceptor)
            .build()
    }

    inline fun <reified T> createApi(): T = getRetrofit().create(T::class.java)

}