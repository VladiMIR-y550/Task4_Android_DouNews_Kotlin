package com.example.task4_android_dounews_kotlin.model

import com.example.task4_android_dounews_kotlin.domain.modelsUi.ArticleUi
import kotlinx.coroutines.flow.Flow

interface NewsListRepository {
    suspend fun getAllNews(): Flow<List<ArticleUi>>
    suspend fun downloadNews(numberPagesLoaded: Int, page: Int)
}