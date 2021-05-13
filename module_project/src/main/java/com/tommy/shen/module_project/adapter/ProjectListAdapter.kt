package com.tommy.shen.module_project.adapter

import androidx.recyclerview.widget.DiffUtil
import com.tommy.shen.module_common.base.BasePagingRecycleAdapter
import com.tommy.shen.module_common.util.clickDelay
import com.tommy.shen.module_common.util.openWeb
import com.tommy.shen.module_project.R
import com.tommy.shen.module_project.data.ArticleData
import com.tommy.shen.module_project.databinding.ItemProjectBinding

class ProjectListAdapter : BasePagingRecycleAdapter<ArticleData, ItemProjectBinding>(object :
    DiffUtil.ItemCallback<ArticleData>() {
    override fun areItemsTheSame(oldItem: ArticleData, newItem: ArticleData): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ArticleData, newItem: ArticleData): Boolean =
        oldItem == newItem

    override fun getChangePayload(oldItem: ArticleData, newItem: ArticleData): Any? = Any()

}) {
    override fun getLayoutId(viewType: Int): Int = R.layout.item_project

    override fun onBindViewHolderCompat(binding: ItemProjectBinding, position: Int) {
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
        binding: ItemProjectBinding,
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