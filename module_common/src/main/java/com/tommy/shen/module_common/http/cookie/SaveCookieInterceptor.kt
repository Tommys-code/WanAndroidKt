package com.tommy.shen.module_common.http.cookie

import com.tencent.mmkv.MMKV
import com.tommy.shen.module_common.util.logout
import okhttp3.Interceptor
import okhttp3.Response

class SaveCookieInterceptor : Interceptor {

    companion object {
        const val SAVE_USER_LOGIN_KEY = "user/login"
        const val SAVE_USER_REGISTER_KEY = "user/register"
        const val LOGOUT_KEY = "user/logout"

        const val SET_COOKIE_KEY = "set-cookie"

        const val COOKIES_PATH = "cookies_path"
        const val COOKIE_NAME = "Cookie"
    }

    private val cookieMMkv by lazy { MMKV.mmkvWithID(COOKIES_PATH) }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val requestUrl = request.url.toString()
        val domain = request.url.host
        // set-cookie maybe has multi, login to save cookie
        if ((requestUrl.contains(SAVE_USER_LOGIN_KEY) || requestUrl.contains(SAVE_USER_REGISTER_KEY))
            && response.headers(SET_COOKIE_KEY).isNotEmpty()
        ) {
            val cookies = response.headers(SET_COOKIE_KEY)
            saveCookie(domain, parseCookie(cookies))
        } else if (requestUrl.contains(LOGOUT_KEY)) {
            clearCookie()
        }
        return response
    }

    private fun parseCookie(it: List<String>): String {
        if (it.isEmpty()) return ""
        val stringBuilder = StringBuilder()
        it.forEach { cookie ->
            stringBuilder.append(cookie).append(";")
        }
        if (stringBuilder.isEmpty()) {
            return ""
        }
        //末尾的";"去掉
        return stringBuilder.deleteCharAt(stringBuilder.length - 1).toString()
    }

    private fun saveCookie(domain: String, cookies: String) {
        cookieMMkv?.encode(domain, cookies)
    }

    private fun clearCookie(){
        cookieMMkv?.clearAll()
        logout()
    }
}