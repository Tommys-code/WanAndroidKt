package com.tommy.shen.module_my.adapter

import androidx.recyclerview.widget.DiffUtil
import com.tommy.shen.module_common.base.BasePagingRecycleAdapter
import com.tommy.shen.module_my.R
import com.tommy.shen.module_my.data.CoinListData
import com.tommy.shen.module_my.databinding.ItemCoinListBinding

class ScoreListAdapter :BasePagingRecycleAdapter<CoinListData,ItemCoinListBinding>(object :DiffUtil.ItemCallback<CoinListData>(){
    override fun areItemsTheSame(oldItem: CoinListData, newItem: CoinListData): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: CoinListData, newItem: CoinListData): Boolean =
        oldItem == newItem

    override fun getChangePayload(oldItem: CoinListData, newItem: CoinListData) = Any()

}){

    override fun getLayoutId(viewType: Int): Int = R.layout.item_coin_list

    override fun onBindViewHolderCompat(binding: ItemCoinListBinding, position: Int) {
        getItem(position)?.run {
            binding.data = this
        }
    }

}