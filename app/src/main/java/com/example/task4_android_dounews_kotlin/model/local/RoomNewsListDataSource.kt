package com.example.task4_android_dounews_kotlin.model.local

import com.example.task4_android_dounews_kotlin.model.INewsListLocalDataSource
import com.example.task4_android_dounews_kotlin.model.local.room.ArticleDbEntity
import com.example.task4_android_dounews_kotlin.model.local.room.NewsListDao
import kotlinx.coroutines.flow.Flow

class RoomNewsListDataSource(private val newsListDao: NewsListDao) :
    INewsListLocalDataSource {
    override fun countDbRows(): Int = newsListDao.countDbRows()

    override suspend fun getAllArticles(): Flow<List<ArticleDbEntity>> =
        newsListDao.getAllArticles()

    override suspend fun saveArticleToDatabase(article: ArticleDbEntity) {
        newsListDao.insert(article)
    }

    override suspend fun updateArticlesInDatabase(article: ArticleDbEntity) {
        newsListDao.update(article)
    }
}