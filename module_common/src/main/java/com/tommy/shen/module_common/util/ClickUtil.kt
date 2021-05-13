package com.tommy.shen.module_common.util

import android.view.View
import com.tommy.shen.module_common.util.ViewClickDelay.SPACE_TIME
import com.tommy.shen.module_common.util.ViewClickDelay.hash
import com.tommy.shen.module_common.util.ViewClickDelay.lastClickTime

/**
 * Created by Administrator on 2019/9/16.
 */
object ViewClickDelay {
    var hash: Int = 0
    var lastClickTime: Long = 0
    var SPACE_TIME: Long = 1000
}

fun View.clickDelay(spaceTime: Long = SPACE_TIME, clickAction: () -> Unit) {
    this.setOnClickListener {
        if (this.hashCode() != hash) {
            hash = this.hashCode()
            lastClickTime = System.currentTimeMillis()
            clickAction()
        } else {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime > spaceTime) {
                lastClickTime = System.currentTimeMillis()
                clickAction()
            }
        }
    }
}