package com.tommy.shen.module_common.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.tommy.shen.module_common.R
import com.tommy.shen.module_common.databinding.ItemLoadMoreBinding

class LoadMoreAdapter(isEmpty: Boolean = true, private val retryCallBack: () -> Unit) :
    LoadStateAdapter<RecyclerCompatVH<ItemLoadMoreBinding>>() {

    override fun onBindViewHolder(
        holder: RecyclerCompatVH<ItemLoadMoreBinding>,
        loadState: LoadState
    ) {
        holder.binding.loadState =
            if (loadState is LoadState.NotLoading && loadState.endOfPaginationReached) 3
            else if (loadState is LoadState.Error) 2
            else 1
        holder.binding.pagingBtnRetry.setOnClickListener {
            retryCallBack.invoke()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): RecyclerCompatVH<ItemLoadMoreBinding> {
        return RecyclerCompatVH(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_load_more,
                parent,
                false
            )
        )
    }

    override fun displayLoadStateAsItem(loadState: LoadState): Boolean {
        return super.displayLoadStateAsItem(loadState) || loadState is LoadState.NotLoading
    }

}