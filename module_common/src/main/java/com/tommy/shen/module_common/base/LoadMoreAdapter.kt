package com.tommy.shen.module_common.base

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.tommy.shen.module_common.R
import com.tommy.shen.module_common.databinding.ItemLoadMoreBinding

class LoadMoreAdapter(private val retryCallBack: () -> Unit) :
    LoadStateAdapter<RecyclerCompatVH<ItemLoadMoreBinding>>() {

    override fun onBindViewHolder(
        holder: RecyclerCompatVH<ItemLoadMoreBinding>,
        loadState: LoadState
    ) {
        Log.i("qwe", loadState.toString())
        holder.binding.loadState =
            if (loadState is LoadState.NotLoading && loadState.endOfPaginationReached) 3
            else if (loadState is LoadState.Error) 2
            else if (loadState is LoadState.Loading) 1
            else 0
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
        return super.displayLoadStateAsItem(loadState) || (loadState is LoadState.NotLoading && loadState.endOfPaginationReached)
    }

}