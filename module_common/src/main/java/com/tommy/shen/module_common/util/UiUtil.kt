package com.tommy.shen.module_common.util

import android.content.Context
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alibaba.android.arouter.launcher.ARouter
import com.tommy.shen.module_common.R
import com.tommy.shen.module_common.constant.Web

fun openWeb(context: Context, url: String, title: String = "", id: Int = -1) {
    ARouter.getInstance().build(Web.WEB_PAGE)
        .withInt("id", id)
        .withString("title", title)
        .withString("url", url)
        .navigation()
}

fun SwipeRefreshLayout.init(refreshListener: (() -> Unit)?) {
    setColorSchemeResources(R.color.colorPrimary)
    setOnRefreshListener { refreshListener?.invoke() }
}

