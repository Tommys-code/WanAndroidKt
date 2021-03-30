package com.tommy.shen.module_common.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

/**
 * Created by Administrator on 2019/10/29.
 */
abstract class RecyclerCompatAdapter<T, VD : ViewDataBinding> constructor(diffCallback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, RecyclerCompatVH<VD>>(diffCallback) {

    constructor() : this(object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T) = true
        override fun areContentsTheSame(oldItem: T, newItem: T) = false
        override fun getChangePayload(oldItem: T, newItem: T) = Any()
    })

    open fun setData(listData: List<T>?) {
        submitList(listData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RecyclerCompatVH<VD>(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), getLayoutId(viewType), parent, false
            )
        )

    override fun onBindViewHolder(holder: RecyclerCompatVH<VD>, position: Int) {
        onBindHolder(holder.binding, position, holder)
    }

    override fun onBindViewHolder(
        holder: RecyclerCompatVH<VD>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        onBindHolder(holder.binding, position, holder, payloads)
    }

    abstract fun getLayoutId(viewType: Int): Int

    abstract fun onBindView(binding: VD, position: Int)

    open fun onBindView(
        binding: VD, position: Int, payloads: MutableList<Any>
    ) {
        onBindView(binding, position)
    }

    open fun onBindHolder(
        binding: VD, position: Int, holder: RecyclerCompatVH<VD>, payloads: MutableList<Any>? = null
    ) {
        if (payloads != null) onBindView(binding, position, payloads)
        else onBindView(binding, position)
    }
}