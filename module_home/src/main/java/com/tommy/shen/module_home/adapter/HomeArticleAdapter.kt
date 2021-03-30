package com.tommy.shen.module_home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.tommy.shen.module_common.base.BasePagingRecycleAdapter
import com.tommy.shen.module_home.R
import com.tommy.shen.module_home.data.ArticleData
import com.tommy.shen.module_home.databinding.ItemArticleBinding

class HomeArticleAdapter : BasePagingRecycleAdapter<ArticleData, ItemArticleBinding>(object :
    DiffUtil.ItemCallback<ArticleData>() {
    override fun areItemsTheSame(oldItem: ArticleData, newItem: ArticleData) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ArticleData, newItem: ArticleData) = oldItem == newItem
    override fun getChangePayload(oldItem: ArticleData, newItem: ArticleData) = Any()
}) {

    override fun getLayoutId(viewType: Int): Int = R.layout.item_article

    override fun onBindViewHolderCompat(binding: ItemArticleBinding, position: Int) {
        getItem(position)?.run {
            binding.data = this
        }
    }

}