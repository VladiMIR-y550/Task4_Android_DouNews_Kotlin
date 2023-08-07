package com.example.task4_android_dounews_kotlin.model

import com.example.task4_android_dounews_kotlin.model.entities.ArticleUi
import kotlinx.coroutines.flow.Flow

interface NewsListRepository {
    suspend fun getAllNews(): Flow<List<ArticleUi>>
    suspend fun getFavoritesNews(): Flow<List<ArticleUi>>
    suspend fun downloadNews(numberPagesLoaded: Int, page: Int)
    suspend fun articleIsSelected (article: ArticleUi)
}