package com.tommy.shen.module_public.adapter

import androidx.recyclerview.widget.DiffUtil
import com.tommy.shen.module_common.base.BasePagingRecycleAdapter
import com.tommy.shen.module_common.util.openWeb
import com.tommy.shen.module_public.R
import com.tommy.shen.module_public.data.ArticleData
import com.tommy.shen.module_public.databinding.PublicItemArticleBinding

class PublicArticleAdapter : BasePagingRecycleAdapter<ArticleData, PublicItemArticleBinding>(object :
    DiffUtil.ItemCallback<ArticleData>() {
    override fun areItemsTheSame(oldItem: ArticleData, newItem: ArticleData) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ArticleData, newItem: ArticleData) = oldItem == newItem
}) {

    override fun getLayoutId(viewType: Int): Int = R.layout.public_item_article

    override fun onBindViewHolderCompat(binding: PublicItemArticleBinding, position: Int) {
        getItem(position)?.run {
            binding.data = this
            binding.root.setOnClickListener { openWeb(link, title, id) }
        }
    }

    override fun setStateRestorationPolicy(strategy: StateRestorationPolicy) {
        super.setStateRestorationPolicy(strategy)
    }
}