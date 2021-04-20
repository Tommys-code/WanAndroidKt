package com.tommy.shen.module_common.util

import android.content.Context
import com.tommy.shen.module_common.widgets.ProgressDialog

/**
 * Created by Administrator on 2020/3/17.
 */
object DialogUtil {

    private var loadingDialog: ProgressDialog? = null

    fun showLoadingDialog(context: Context, msg: String? = null, cancelable: Boolean = true) {
        if (loadingDialog == null) {
            loadingDialog = ProgressDialog(context, cancelable)
                .setContent(msg)
                .setDismissListener { loadingDialog = null }
            loadingDialog?.show()
        }
    }

    fun dismissLoadingDialog() {
        if (loadingDialog?.isShowing == true) loadingDialog?.dismiss()
    }
}