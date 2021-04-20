package com.tommy.shen.module_common.widgets

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.tommy.shen.module_common.R
import com.tommy.shen.module_common.databinding.DialogProgressBinding

/**
 * Created by Administrator on 2020/3/17.
 */
class ProgressDialog(context: Context, mCancelable: Boolean = true) :
    Dialog(context, R.style.DialogLoadingTheme) {

    private val dialogBinding = DataBindingUtil.inflate<DialogProgressBinding>(
        LayoutInflater.from(context), R.layout.dialog_progress, null, false
    )

    init {
        setCanceledOnTouchOutside(mCancelable)
        setCancelable(mCancelable)
        setContentView(dialogBinding.root)
    }

    fun setContent(content: String?): ProgressDialog {
        dialogBinding.msg = content ?: "数据加载中..."
        return this
    }

    fun setDismissListener(dismiss: (() -> Unit)?): ProgressDialog {
        setOnDismissListener { dismiss?.invoke() }
        return this
    }

}