package com.tommy.shen.module_common.widgets

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.LayoutInflater.Factory2
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * 全局替换TextView
 */
class MyLayoutFactory(mContext: Context) : Factory2 {

    companion object {

        fun inject(context: Context) {
            val inflater = if (context is Activity) {
                context.layoutInflater
            } else {
                LayoutInflater.from(context)
            }
            if (inflater.factory2 == null) {
                val factory = MyLayoutFactory(context)
                inflater.factory2 = factory
            }
        }

    }

    private var mViewCreateFactory: LayoutInflater.Factory? = null

    init {
        if (mContext is AppCompatActivity) {
            mViewCreateFactory = LayoutInflater.Factory { name, context, attrs ->
                mContext.delegate.createView(null, name, context, attrs)
            }
        }
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        return onCreateView(name, context, attrs)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        //如果是CustomTextView，代表已经进行了设置，无需再次创建，留给系统创建就行
        if (name.startsWith("com.tommy.shen.module_common.widgets.CustomTextView")) {
            return null
        }
        val view: View? = mViewCreateFactory?.onCreateView(name, context, attrs)
        return initOwn(name, context, attrs, view)
    }

    private fun initOwn(name: String, context: Context, attrs: AttributeSet, view: View?): View? {
        if (TextUtils.equals(name, "TextView")) {
            return CustomTextView(context, attrs)
        }
        return view
    }

}