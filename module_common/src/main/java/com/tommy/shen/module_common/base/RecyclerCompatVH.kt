package com.tommy.shen.module_common.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Administrator on 2019/10/29.
 */
class RecyclerCompatVH<VD : ViewDataBinding>(val binding: VD) :
    RecyclerView.ViewHolder(binding.root)