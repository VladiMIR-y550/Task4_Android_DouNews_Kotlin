package com.example.task4_android_dounews_kotlin.model

import com.example.task4_android_dounews_kotlin.model.local.room.ArticleDbEntity
import kotlinx.coroutines.flow.Flow

interface INewsListLocalDataSource {
    fun countDbRows(): Int

    suspend fun getAllArticles(): Flow<List<ArticleDbEntity>>

    suspend fun saveArticleToDatabase(article: ArticleDbEntity)

    suspend fun updateArticlesInDatabase(article: ArticleDbEntity)
}