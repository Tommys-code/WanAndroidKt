package com.tommy.shen.module_common.http.cookie

import com.tencent.mmkv.MMKV
import okhttp3.Interceptor
import okhttp3.Response

class LoginInterceptor : Interceptor {

    private val cookieMMkv by lazy { MMKV.mmkvWithID(SaveCookieInterceptor.COOKIES_PATH) }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        val domain = request.url.host

        if (domain.isNotEmpty()) {
            val mCookie = cookieMMkv?.decodeString(domain) ?: ""
            if (mCookie.isNotEmpty()) {
                builder.addHeader(SaveCookieInterceptor.COOKIE_NAME, mCookie)
            }
        }
        return chain.proceed(builder.build())
    }

}