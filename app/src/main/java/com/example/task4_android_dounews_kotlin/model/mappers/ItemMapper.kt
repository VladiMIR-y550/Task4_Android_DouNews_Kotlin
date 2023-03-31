package com.example.task4_android_dounews_kotlin.model.mappers

import com.example.task4_android_dounews_kotlin.domain.modelsUi.ArticleUi
import com.example.task4_android_dounews_kotlin.model.local.room.ArticleDbEntity
import com.example.task4_android_dounews_kotlin.model.remote.pojo.NewsResponseEntity
import com.example.task4_android_dounews_kotlin.utils.convertStringToDate
import com.example.task4_android_dounews_kotlin.utils.formatDateToLong
import com.example.task4_android_dounews_kotlin.utils.refactorPublished
import javax.inject.Inject

class ItemMapper @Inject constructor() :
    NewsMapper<ArticleDbEntity, ArticleUi, NewsResponseEntity> {

    override fun fromDbEntity(dbEntity: ArticleDbEntity): ArticleUi {
        return ArticleUi(
            id = dbEntity.id,
            title = dbEntity.title,
            url = dbEntity.url,
            category = dbEntity.category,
            categoryUrl = dbEntity.categoryUrl,
            announcement = dbEntity.announcement,
            tags = dbEntity.tags,
            pageviews = dbEntity.pageviews,
            commentsCount = dbEntity.commentsCount,
            imgBig = dbEntity.imgBig,
            imgBig2x = dbEntity.imgBig2x,
            imgSmall = dbEntity.imgSmall,
            imgSmall2x = dbEntity.imgSmall2x,
            authorName = dbEntity.authorName,
            authorUrl = dbEntity.authorUrl,
            published = dbEntity.published,
            date = dbEntity.date,
            dateLong = dbEntity.dateLong
        )
    }

    override fun fromUiEntity(uiEntity: ArticleUi): ArticleDbEntity {
        return ArticleDbEntity(
            id = uiEntity.id,
            title = uiEntity.title,
            url = uiEntity.url,
            category = uiEntity.category,
            categoryUrl = uiEntity.categoryUrl,
            announcement = uiEntity.announcement,
            tags = uiEntity.tags,
            pageviews = uiEntity.pageviews,
            commentsCount = uiEntity.commentsCount,
            imgBig = uiEntity.imgBig,
            imgBig2x = uiEntity.imgBig2x,
            imgSmall = uiEntity.imgSmall,
            imgSmall2x = uiEntity.imgSmall2x,
            authorName = uiEntity.authorName,
            authorUrl = uiEntity.authorUrl,
            published = uiEntity.published,
            date = uiEntity.date,
            dateLong = uiEntity.dateLong
        )
    }

    override fun fromResponseEntity(responseEntity: NewsResponseEntity): ArticleDbEntity {
        return ArticleDbEntity(
            id = responseEntity.id,
            title = responseEntity.title,
            url = responseEntity.url,
            category = responseEntity.category,
            categoryUrl = responseEntity.categoryUrl,
            announcement = responseEntity.announcement,
            tags = responseEntity.tags,
            pageviews = responseEntity.pageviews,
            commentsCount = responseEntity.commentsCount,
            imgBig = responseEntity.imgBig,
            imgBig2x = responseEntity.imgBig2x,
            imgSmall = responseEntity.imgSmall,
            imgSmall2x = responseEntity.imgSmall2x,
            authorName = responseEntity.authorName,
            authorUrl = responseEntity.authorUrl,
            published = responseEntity.published,
            date = refactorPublished(convertStringToDate(responseEntity.published)),
            dateLong = formatDateToLong(convertStringToDate(responseEntity.published))
        )
    }

    override fun toUiEntityList(dbEntityList: List<ArticleDbEntity>): List<ArticleUi> {
        return dbEntityList.map { fromDbEntity(it) }
    }

    override fun fromResponseEntityList(responseEntityList: List<NewsResponseEntity>): List<ArticleDbEntity> {
        return responseEntityList.map {
            fromResponseEntity(it)
        }
    }
}