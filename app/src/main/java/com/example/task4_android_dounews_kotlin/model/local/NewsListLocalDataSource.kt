package com.example.task4_android_dounews_kotlin.model.local

import com.example.task4_android_dounews_kotlin.model.entities.ArticleUi
import com.example.task4_android_dounews_kotlin.model.entities.ArticlesWithSetting
import com.example.task4_android_dounews_kotlin.model.local.room.ArticleDbEntity
import kotlinx.coroutines.flow.Flow

interface NewsListLocalDataSource {
    fun countDbRows(): Int
    suspend fun getAllArticlesFlow(): Flow<List<ArticlesWithSetting>>
    suspend fun getAllArticles(): List<ArticlesWithSetting>
    suspend fun getFavoritesNews(): Flow<List<ArticlesWithSetting>>
    suspend fun saveArticleToDatabase(article: ArticleDbEntity)
    suspend fun updateArticlesInDatabase(article: ArticleDbEntity)
    suspend fun saveIsSelected(articleDbEntity: ArticleDbEntity)
    suspend fun updateIsSelected(articleUi: ArticleUi)
}