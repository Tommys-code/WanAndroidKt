package com.tommy.shen.module_my.data

data class ArticleData(
    val id: Int,
    val originId: Int,
    val author: String,
    val niceDate: String,
    val title: String,
    val link: String,
    val chapterName: String,
    @Transient var collect: Boolean = true
)