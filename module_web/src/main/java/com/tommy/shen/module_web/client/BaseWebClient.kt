package com.tommy.shen.module_web.client

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.tencent.smtt.export.external.interfaces.SslError
import com.tencent.smtt.export.external.interfaces.SslErrorHandler
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.export.external.interfaces.WebResourceResponse
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient


open class BaseWebClient : WebViewClient() {

    private val TAG = "BaseWebClient"

    // 拦截的网址
    private val blackHostList = arrayListOf(
        "www.taobao.com",
        "www.jd.com",
        "yun.tuisnake.com",
        "yun.lvehaisen.com",
        "yun.tuitiger.com"
    )

    private fun isBlackHost(host: String): Boolean {
        for (blackHost in blackHostList) {
            if (blackHost == host) {
                return true
            }
        }
        return false
    }

    private fun shouldInterceptRequest(uri: Uri?): Boolean {
        if (uri != null) {
            val host = uri.host ?: ""
            return isBlackHost(host)
        }
        return false
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun shouldInterceptRequest(
        view: WebView?,
        request: WebResourceRequest?
    ): WebResourceResponse? {
        if (shouldInterceptRequest(request?.url)) {
            return WebResourceResponse(null, null, null)
        }
        return super.shouldInterceptRequest(view, request)
    }

    override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse? {
        if (shouldInterceptRequest(Uri.parse(url))) {
            return WebResourceResponse(null, null, null)
        }
        return super.shouldInterceptRequest(view, url)
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        return url?.let {
            if (it.startsWith("http://") || it.startsWith("https://")) {
                view?.loadUrl(url)
                false
            } else {
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    view?.context?.startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                true
            }
        } ?: false
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
    }

    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        // super.onReceivedSslError(view, handler, error)
        handler?.proceed()
    }

}