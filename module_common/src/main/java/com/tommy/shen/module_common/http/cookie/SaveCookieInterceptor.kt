package com.tommy.shen.module_common.http.cookie

import com.tencent.mmkv.MMKV
import okhttp3.Interceptor
import okhttp3.Response

class SaveCookieInterceptor : Interceptor {

    companion object {
        const val SAVE_USER_LOGIN_KEY = "user/login"
        const val SAVE_USER_REGISTER_KEY = "user/register"

        const val SET_COOKIE_KEY = "set-cookie"

        const val COOKIES_PATH = "cookies_path"
    }

    private val cookie by lazy { MMKV.mmkvWithID(COOKIES_PATH) }

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
            val cookie = encodeCookie(cookies)
            saveCookie(requestUrl, domain, cookie)
        }
        return response
    }

    private fun encodeCookie(cookies: List<String>): String {
        val sb = StringBuilder()
        val set = HashSet<String>()
        cookies.map { cookie ->
            cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        }.forEach {
            it.filterNot { filter -> set.contains(filter) }.forEach { each -> set.add(each) }
        }
        val ite = set.iterator()
        while (ite.hasNext()) {
            val cookie = ite.next()
            sb.append(cookie).append(";")
        }
        val last = sb.lastIndexOf(";")
        if (sb.length - 1 == last) {
            sb.deleteCharAt(last)
        }
        return sb.toString()
    }

    private fun saveCookie(url: String?, domain: String?, cookies: String) {
        url ?: return
        cookie?.encode(url, cookies)
        domain ?: return
        cookie?.encode(domain, cookies)
    }
}