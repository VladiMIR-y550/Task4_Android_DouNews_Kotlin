package com.example.task4_android_dounews_kotlin.utils

import android.annotation.SuppressLint
import com.example.task4_android_dounews_kotlin.model.NewsListRepository
import com.example.task4_android_dounews_kotlin.model.local.room.ArticleDbEntity
import com.example.task4_android_dounews_kotlin.model.remote.pojo.NewsResponseEntity
import java.text.SimpleDateFormat
import java.util.*

fun NewsListRepository.toArticleDbEntity(newsResponse: NewsResponseEntity): ArticleDbEntity =
    ArticleDbEntity(
        id = newsResponse.id,
        title = newsResponse.title,
        url = newsResponse.url,
        category = newsResponse.category,
        categoryUrl = newsResponse.categoryUrl,
        announcement = newsResponse.announcement,
        tags = newsResponse.tags,
        pageviews = newsResponse.pageviews,
        commentsCount = newsResponse.commentsCount,
        imgBig = newsResponse.imgBig,
        imgBig2x = newsResponse.imgBig2x,
        imgSmall = newsResponse.imgSmall,
        imgSmall2x = newsResponse.imgSmall2x,
        authorName = newsResponse.authorName,
        authorUrl = newsResponse.authorUrl,
        published = newsResponse.published,
        date = refactorPublished(convertStringToDate(newsResponse.published)),
        dateLong = formatDateToLong(convertStringToDate(newsResponse.published))
    )

@SuppressLint("SimpleDateFormat")
fun convertStringToDate(dataString: String): Date =
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dataString) as Date

@SuppressLint("SimpleDateFormat")
fun refactorPublished(date: Date): String =
    SimpleDateFormat("d MMMM yyyy H:mm").format(date)

fun formatDateToLong(date: Date): Long {
    return date.time
}