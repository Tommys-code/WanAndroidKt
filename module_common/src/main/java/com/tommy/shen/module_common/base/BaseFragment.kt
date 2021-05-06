package com.tommy.shen.module_common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.tommy.shen.module_common.databinding.ToolbarLayoutBinding

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    protected lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreate()
    }

    abstract fun getLayoutId(): Int

    abstract fun onCreate()

    protected fun ToolbarLayoutBinding.init(title: String) {
        apply {
            back.visibility = View.GONE
            titleTv.text = title
        }
    }

    protected fun ToolbarLayoutBinding.init(
        @StringRes title: Int = -1,
        @DrawableRes icon: Int = 0,
        rightClick: (() -> Unit)? = null
    ) {
        apply {
            back.visibility = View.GONE
            if(title != -1) titleTv.text = getString(title)
            if (icon != 0) {
                icRight.visibility = View.VISIBLE
                icRight.setImageResource(icon)
                icRight.setOnClickListener { rightClick?.invoke() }
            }
        }
    }
}