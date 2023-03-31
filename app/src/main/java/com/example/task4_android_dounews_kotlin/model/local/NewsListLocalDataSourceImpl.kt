package com.example.task4_android_dounews_kotlin.model.local

import com.example.task4_android_dounews_kotlin.model.local.room.ArticleDbEntity
import com.example.task4_android_dounews_kotlin.model.local.room.NewsListDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsListLocalDataSourceImpl @Inject constructor(
    private val newsListDao: NewsListDao
) : NewsListLocalDataSource {
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