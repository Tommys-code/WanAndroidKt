package com.tommy.shen.module_my.adapter

import com.tommy.shen.module_common.base.RecyclerCompatAdapter
import com.tommy.shen.module_my.R
import com.tommy.shen.module_my.databinding.ItemScoreHeadBinding

class ScoreHeadAdapter : RecyclerCompatAdapter<Int, ItemScoreHeadBinding>() {

    override fun getLayoutId(viewType: Int): Int = R.layout.item_score_head

    override fun onBindView(binding: ItemScoreHeadBinding, position: Int) {
        getItem(position).run {

        }
    }
}