package com.tommy.shen.module_common.base

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DiffUtil

abstract class BasePagingRecycleAdapter<T : Any, VD : ViewDataBinding>(
    itemCallback: DiffUtil.ItemCallback<T> = object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T) = true
        override fun areContentsTheSame(oldItem: T, newItem: T) = false
        override fun getChangePayload(oldItem: T, newItem: T) = Any()
    }
) : PagingDataAdapter<T, RecyclerCompatVH<VD>>(itemCallback) {

    fun addFooter(): ConcatAdapter = withLoadStateFooter(LoadMoreAdapter { retry() })

    protected abstract fun getLayoutId(viewType: Int): Int

    protected abstract fun onBindViewHolderCompat(binding: VD, position: Int)

    override fun onBindViewHolder(holder: RecyclerCompatVH<VD>, position: Int) {
        onBindViewHolderCompat(holder.binding, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerCompatVH<VD> {
        return RecyclerCompatVH(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                getLayoutId(viewType),
                parent,
                false
            )
        )
    }

}