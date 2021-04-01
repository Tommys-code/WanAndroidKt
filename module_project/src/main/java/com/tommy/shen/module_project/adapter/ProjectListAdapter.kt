package com.tommy.shen.module_project.adapter

import androidx.recyclerview.widget.DiffUtil
import com.tommy.shen.module_common.base.BasePagingRecycleAdapter
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
            binding.root.setOnClickListener { openWeb(link, title, id) }
        }
    }

}