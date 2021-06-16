package com.tommy.shen.module_common.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.tommy.shen.module_common.databinding.ToolbarLayoutBinding
import com.tommy.shen.module_common.widgets.MyLayoutFactory


abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        MyLayoutFactory.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.lifecycleOwner = this
        onCreate()
    }

    abstract fun getLayoutId(): Int

    abstract fun onCreate()

    protected fun ToolbarLayoutBinding.init(title: String, isShowBack: Boolean = true) {
        apply {
            back.visibility = if (isShowBack) View.VISIBLE else View.GONE
            back.setOnClickListener { finish() }
            titleTv.text = title
        }
    }
}