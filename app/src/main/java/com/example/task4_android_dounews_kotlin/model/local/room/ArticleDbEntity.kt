package com.example.task4_android_dounews_kotlin.model.local.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.task4_android_dounews_kotlin.model.local.room.ArticleDbEntity.Companion.TABLE_NAME
import com.example.task4_android_dounews_kotlin.model.remote.pojo.NewsResponseEntity
import com.example.task4_android_dounews_kotlin.utils.convertStringToDate
import com.example.task4_android_dounews_kotlin.utils.formatDateToLong
import com.example.task4_android_dounews_kotlin.utils.refactorPublished

@Entity(tableName = TABLE_NAME)
data class ArticleDbEntity(
    @PrimaryKey
    val id: Long,
    @ColumnInfo
    val title: String,
    @ColumnInfo
    val url: String,
    @ColumnInfo
    val category: String,
    @ColumnInfo(name = "category_url")
    val categoryUrl: String,
    @ColumnInfo
    val announcement: String,
    @ColumnInfo
    val tags: String,
    @ColumnInfo
    var pageviews: Int,
    @ColumnInfo(name = "comments_count")
    var commentsCount: Int,
    @ColumnInfo(name = "img_big")
    val imgBig: String?,
    @ColumnInfo(name = "img_big_2x")
    val imgBig2x: String,
    @ColumnInfo(name = "img_small")
    val imgSmall: String,
    @ColumnInfo(name = "img_small_2x")
    val imgSmall2x: String,
    @ColumnInfo
    val authorName: String,
    @ColumnInfo(name = "author_url")
    val authorUrl: String,
    @ColumnInfo
    var published: String = "",
    @ColumnInfo
    val date: String,
    @ColumnInfo(name = "date_long")
    val dateLong: Long
) {
    companion object {
        const val TABLE_NAME = "articles_table"
        fun fromNewsResponseEntity(newsResponseEntity: NewsResponseEntity): ArticleDbEntity {
            return ArticleDbEntity(
                id = newsResponseEntity.id,
                title = newsResponseEntity.title,
                url = newsResponseEntity.url,
                category = newsResponseEntity.category,
                categoryUrl = newsResponseEntity.categoryUrl,
                announcement = newsResponseEntity.announcement,
                tags = newsResponseEntity.tags,
                pageviews = newsResponseEntity.pageviews,
                commentsCount = newsResponseEntity.commentsCount,
                imgBig = newsResponseEntity.imgBig,
                imgBig2x = newsResponseEntity.imgBig2x,
                imgSmall = newsResponseEntity.imgSmall,
                imgSmall2x = newsResponseEntity.imgSmall2x,
                authorName = newsResponseEntity.authorName,
                authorUrl = newsResponseEntity.authorUrl,
                published = newsResponseEntity.published,
                date = refactorPublished(convertStringToDate(newsResponseEntity.published)),
                dateLong = formatDateToLong(convertStringToDate(newsResponseEntity.published))
            )
        }
    }
}