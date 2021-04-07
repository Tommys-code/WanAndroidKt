package com.tommy.shen.module_web.act

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tommy.shen.module_common.base.BaseActivity
import com.tommy.shen.module_common.constant.Web
import com.tommy.shen.module_web.R
import com.tommy.shen.module_web.client.BaseWebClient
import com.tommy.shen.module_web.client.JianShuWebClient
import com.tommy.shen.module_web.client.JianShuWebClient.Companion.JIAN_SHU
import com.tommy.shen.module_web.databinding.ActWebBinding

@Route(path = Web.WEB_PAGE)
class WebActivity : BaseActivity<ActWebBinding>() {

    @JvmField
    @Autowired
    var title: String = ""

    @JvmField
    @Autowired
    var url: String = ""

    @JvmField
    @Autowired
    var id: Int = -1
    private lateinit var webView: WebView

    override fun getLayoutId(): Int = R.layout.act_web

    override fun onCreate() {
        ARouter.getInstance().inject(this)
        intent?.let {
            title = it.getStringExtra("title") ?: ""
            url = it.getStringExtra("url") ?: ""
            id = it.getIntExtra("id", -1)
        }

        binding.toolbar.init(title)

        webView = WebView(this)
        initWebSetting()
        initWebClient()

        webView.layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        (binding.root as LinearLayout).addView(webView)

        webView.loadUrl(url)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebSetting() {
        WebView.setWebContentsDebuggingEnabled(true)
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            setAppCacheMaxSize((1024 * 1024 * 4).toLong())
            val appCachePath = applicationContext.cacheDir.absolutePath
            setAppCachePath(appCachePath)
            allowFileAccess = true
            setAppCacheEnabled(true)
        }

        webView.x5WebViewExtension.invokeMiscMethod("setVideoParams", Bundle().apply {
            //true表示标准全屏，false表示X5全屏；不设置默认false，
            putBoolean("standardFullScreen", false)
            //false：关闭小窗；true：开启小窗；不设置默认true，
            putBoolean("supportLiteWnd", true)
            //1：以页面内开始播放，2：以全屏开始播放；不设置默认：1
            putInt("DefaultVideoScreen", 2)
        })
    }

    private fun initWebClient() {
        webView.webViewClient = BaseWebClient()
//            if (url.startsWith(JIAN_SHU)) JianShuWebClient() else BaseWebClient()
        webView.webChromeClient = BaseWebChromeClient()
    }

    override fun onDestroy() {
        val parent = webView.parent
        (parent as ViewGroup?)?.removeView(webView)
        webView.stopLoading()
        webView.settings.javaScriptEnabled = false
        webView.removeAllViews()
        webView.destroy()
        super.onDestroy()
    }

    inner class BaseWebChromeClient : WebChromeClient() {

        override fun onProgressChanged(p0: WebView?, p1: Int) {
            super.onProgressChanged(p0, p1)
            binding.progressBar.progress = p1
            binding.progressBar.visibility = if (p1 == 100) View.GONE else View.VISIBLE
        }

        override fun onShowCustomView(p0: View?, p1: IX5WebChromeClient.CustomViewCallback?) {
            super.onShowCustomView(p0, p1)
        }

    }

}