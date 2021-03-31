package com.tommy.shen.module_home.adapter

import com.tommy.shen.module_common.base.RecyclerCompatAdapter
import com.tommy.shen.module_home.R
import com.tommy.shen.module_home.databinding.ItemSearchKeyBinding

class SearchKeyAdapter : RecyclerCompatAdapter<String, ItemSearchKeyBinding>() {

    override fun getLayoutId(viewType: Int): Int = R.layout.item_search_key

    override fun onBindView(binding: ItemSearchKeyBinding, position: Int) {
        getItem(position)?.run {
            binding.name = this
            binding.root.setOnClickListener { onKeyClickListener?.invoke(this) }
        }
    }

    private var onKeyClickListener: ((key: String) -> Unit)? = null

    fun setKeyClickListener(onKeyClickListener: ((key: String) -> Unit)) {
        this.onKeyClickListener = onKeyClickListener
    }

}