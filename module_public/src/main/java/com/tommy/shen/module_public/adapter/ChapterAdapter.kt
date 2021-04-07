package com.tommy.shen.module_public.adapter

import com.tommy.shen.module_common.base.RecyclerCompatAdapter
import com.tommy.shen.module_public.R
import com.tommy.shen.module_public.data.WxArticleData
import com.tommy.shen.module_public.databinding.PublicItemChapterBinding

class ChapterAdapter : RecyclerCompatAdapter<WxArticleData, PublicItemChapterBinding>() {

    var selectedPos = 0

    override fun getLayoutId(viewType: Int): Int = R.layout.public_item_chapter

    override fun onBindView(binding: PublicItemChapterBinding, position: Int) {
        getItem(position)?.run {
            binding.isSelected = position == selectedPos
            binding.data = this
            binding.root.setOnClickListener {
                selectedPos = position
                onClickListener?.invoke(this)
                notifyDataSetChanged()
            }
        }
    }

    private var onClickListener: ((data: WxArticleData) -> Unit)? = null
    fun setOnItemClickListener(onClickListener: ((data: WxArticleData) -> Unit)) {
        this.onClickListener = onClickListener
    }

}