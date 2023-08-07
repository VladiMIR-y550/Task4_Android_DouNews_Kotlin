package com.example.task4_android_dounews_kotlin.model.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.example.task4_android_dounews_kotlin.model.local.room.ArticleDbEntity
import com.example.task4_android_dounews_kotlin.model.local.room.ArticleSettingsDbEntity

data class ArticlesWithSetting(
    @Embedded val articleDbEntity: ArticleDbEntity,
    @Relation(parentColumn = "id", entityColumn = "setting_id")
    val articleDbSettingsDbEntity: ArticleSettingsDbEntity?
) {

    fun toArticleUi(): ArticleUi {
        return ArticleUi(
            id = articleDbEntity.id,
            title = articleDbEntity.title,
            url = articleDbEntity.url,
            category = articleDbEntity.categoryUrl,
            categoryUrl = articleDbEntity.categoryUrl,
            announcement = articleDbEntity.announcement,
            tags = articleDbEntity.tags,
            pageviews = articleDbEntity.pageviews,
            commentsCount = articleDbEntity.commentsCount,
            imgBig = articleDbEntity.imgBig,
            imgBig2x = articleDbEntity.imgBig2x,
            imgSmall = articleDbEntity.imgSmall,
            imgSmall2x = articleDbEntity.imgSmall2x,
            authorUrl = articleDbEntity.authorUrl,
            authorName = articleDbEntity.authorName,
            published = articleDbEntity.published,
            date = articleDbEntity.date,
            dateLong = articleDbEntity.dateLong,
            isSelected = articleDbSettingsDbEntity?.isSelected ?: false
        )
    }
}