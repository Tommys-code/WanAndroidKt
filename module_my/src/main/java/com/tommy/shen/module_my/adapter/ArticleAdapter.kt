package com.tommy.shen.module_my.adapter

import androidx.recyclerview.widget.DiffUtil
import com.tommy.shen.module_common.base.BasePagingRecycleAdapter
import com.tommy.shen.module_common.util.openWeb
import com.tommy.shen.module_my.R
import com.tommy.shen.module_my.data.ArticleData
import com.tommy.shen.module_my.databinding.ItemMyArticleBinding

class ArticleAdapter : BasePagingRecycleAdapter<ArticleData, ItemMyArticleBinding>(object :
    DiffUtil.ItemCallback<ArticleData>() {
    override fun areItemsTheSame(oldItem: ArticleData, newItem: ArticleData) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ArticleData, newItem: ArticleData) = oldItem == newItem
    override fun getChangePayload(oldItem: ArticleData, newItem: ArticleData) = Any()
}) {

    override fun getLayoutId(viewType: Int): Int = R.layout.item_my_article

    override fun onBindViewHolderCompat(binding: ItemMyArticleBinding, position: Int) {
        getItem(position)?.run {
            binding.data = this
            binding.root.setOnClickListener { openWeb(link, title, id) }
            binding.collectTv.setOnClickListener {
                collect = false
                onCollectListener?.invoke(originId, position)
            }
        }
    }

    private var onCollectListener: ((id: Int, position: Int) -> Unit)? = null

    fun setOnCollectedListener(onCollectListener: ((id: Int, position: Int) -> Unit)) {
        this.onCollectListener = onCollectListener
    }

}