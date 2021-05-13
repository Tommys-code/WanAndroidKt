package com.tommy.shen.module_project.data

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
    val envelopePic: String,
    val desc: String,
    var collect: Boolean
)