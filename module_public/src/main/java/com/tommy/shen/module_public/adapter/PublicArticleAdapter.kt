package com.tommy.shen.module_public.adapter

import androidx.recyclerview.widget.DiffUtil
import com.tommy.shen.module_common.base.BasePagingRecycleAdapter
import com.tommy.shen.module_common.util.clickDelay
import com.tommy.shen.module_common.util.openWeb
import com.tommy.shen.module_public.R
import com.tommy.shen.module_public.data.ArticleData
import com.tommy.shen.module_public.databinding.PublicItemArticleBinding

class PublicArticleAdapter :
    BasePagingRecycleAdapter<ArticleData, PublicItemArticleBinding>(object :
        DiffUtil.ItemCallback<ArticleData>() {
        override fun areItemsTheSame(oldItem: ArticleData, newItem: ArticleData) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ArticleData, newItem: ArticleData) =
            oldItem == newItem
    }) {

    override fun getLayoutId(viewType: Int): Int = R.layout.public_item_article

    override fun onBindViewHolderCompat(binding: PublicItemArticleBinding, position: Int) {
        getItem(position)?.run {
            binding.data = this
            binding.collect = collect
            binding.root.setOnClickListener { openWeb(link, title, id) }
            binding.collectTv.clickDelay {
                onCollectListener?.invoke(collect, id, position)
            }
        }
    }

    override fun onBindViewHolderCompat(
        binding: PublicItemArticleBinding,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.contains("collect")) {
            getItem(position)?.run {
                binding.collect = collect
            }
        }
    }

    private var onCollectListener: ((collect: Boolean, id: Int, position: Int) -> Unit)? = null

    fun setOnCollectedListener(onCollectListener: ((collect: Boolean, id: Int, position: Int) -> Unit)) {
        this.onCollectListener = onCollectListener
    }

    fun setItemCollected(position: Int): Boolean {
        getItem(position)?.run { collect = !collect }
        notifyItemChanged(position, "collect")
        return getItem(position)?.collect == true
    }
}