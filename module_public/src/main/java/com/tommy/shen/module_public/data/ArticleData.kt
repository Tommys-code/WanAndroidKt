package com.tommy.shen.module_public.data

data class ArticleData(
    val id: Int,
    val author: String,
    val shareUser: String,
    val niceDate: String,
    val title: String,
    val publishTime: Long,
    val link: String,
    val superChapterName: String,
    val chapterName: String,
    var collect: Boolean
) {

    fun isNew() = publishTime >= System.currentTimeMillis() - 24 * 60 * 60 * 1000

}