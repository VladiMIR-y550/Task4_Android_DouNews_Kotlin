package com.example.task4_android_dounews_kotlin.model.local.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.task4_android_dounews_kotlin.model.local.room.ArticleDbEntity.Companion.TABLE_NAME

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
    @ColumnInfo
    val categoryUrl: String,
    @ColumnInfo
    val announcement: String,
    @ColumnInfo
    val tags: String,
    @ColumnInfo
    var pageviews: Int,
    @ColumnInfo
    var commentsCount: Int,
    @ColumnInfo
    val imgBig: String?,
    @ColumnInfo
    val imgBig2x: String,
    @ColumnInfo
    val imgSmall: String,
    @ColumnInfo
    val imgSmall2x: String,
    @ColumnInfo
    val authorName: String,
    @ColumnInfo
    val authorUrl: String,
    @ColumnInfo
    var published: String = "",
    @ColumnInfo
    val date: String,
    @ColumnInfo
    val dateLong: Long
) {
    companion object {
        const val TABLE_NAME = "articles_table"
    }
}