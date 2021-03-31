package com.tommy.shen.module_common.util

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alibaba.android.arouter.launcher.ARouter
import com.tommy.shen.module_common.R
import com.tommy.shen.module_common.constant.Web
import com.tommy.shen.module_common.http.NetWork

fun openWeb(url: String, title: String = "", id: Int = -1) {
    ARouter.getInstance().build(Web.WEB_PAGE)
        .withInt("id", id)
        .withString("title", title)
        .withString("url", if (url.startsWith("http")) url else "${NetWork.BASE_URL}url")
        .navigation()
}

fun SwipeRefreshLayout.init(refreshListener: (() -> Unit)?) {
    setColorSchemeResources(R.color.colorPrimary)
    setOnRefreshListener { refreshListener?.invoke() }
}

