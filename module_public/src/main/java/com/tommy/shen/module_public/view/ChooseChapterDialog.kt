package com.tommy.shen.module_public.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tommy.shen.module_public.R
import com.tommy.shen.module_public.adapter.ChapterAdapter
import com.tommy.shen.module_public.data.WxArticleData
import com.tommy.shen.module_public.databinding.DialogChooseChapterBinding


class ChooseChapterDialog(context: Context) : BottomSheetDialog(context) {

    private val binding = DataBindingUtil.inflate<DialogChooseChapterBinding>(
        LayoutInflater.from(context),
        R.layout.dialog_choose_chapter,
        null,
        false
    )
    private val adapter by lazy {
        ChapterAdapter().apply {
            setOnItemClickListener {
                onClickListener?.invoke(it)
                dismiss()
            }
        }
    }

    init {
        setContentView(binding.root)
        try {
            (binding.root.parent as ViewGroup).setBackgroundResource(R.drawable.chapter_dialog_bg)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val manager = binding.recycle.layoutManager as FlexboxLayoutManager
        manager.flexDirection = FlexDirection.ROW
        manager.flexWrap = FlexWrap.WRAP
        manager.alignItems = AlignItems.STRETCH
        binding.recycle.adapter = adapter
    }

    fun setData(data: List<WxArticleData>) {
        adapter.submitList(data)
    }

    private var onClickListener: ((data: WxArticleData) -> Unit)? = null
    fun setOnItemClickListener(onClickListener: ((data: WxArticleData) -> Unit)) {
        this.onClickListener = onClickListener
    }

}