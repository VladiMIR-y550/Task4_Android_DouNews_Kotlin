package com.example.task4_android_dounews_kotlin.domain.modelsUi

data class ArticleUi(
    val id: Long,
    val title: String,
    val url: String,
    val category: String,
    val categoryUrl: String,
    val announcement: String,
    val tags: String,
    var pageviews: Int,
    var commentsCount: Int,
    val imgBig: String?,
    val imgBig2x: String,
    val imgSmall: String,
    val imgSmall2x: String,
    val authorName: String,
    val authorUrl: String,
    var published: String = "",
    val date: String,
    val dateLong: Long
)