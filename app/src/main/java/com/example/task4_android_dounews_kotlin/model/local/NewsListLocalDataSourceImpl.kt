package com.example.task4_android_dounews_kotlin.model.local

import com.example.task4_android_dounews_kotlin.model.entities.ArticleUi
import com.example.task4_android_dounews_kotlin.model.entities.ArticlesWithSetting
import com.example.task4_android_dounews_kotlin.model.local.room.ArticleDbEntity
import com.example.task4_android_dounews_kotlin.model.local.room.ArticleSettingsDbEntity
import com.example.task4_android_dounews_kotlin.model.local.room.NewsListDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsListLocalDataSourceImpl @Inject constructor(
    private val newsListDao: NewsListDao
) : NewsListLocalDataSource {
    override fun countDbRows(): Int = newsListDao.countDbRows()

    override suspend fun getAllArticlesFlow(): Flow<List<ArticlesWithSetting>> {
        return newsListDao.getAllNewsFlow()
    }

    override suspend fun getAllArticles(): List<ArticlesWithSetting> {
        return newsListDao.getAllNews()
    }

    override suspend fun getFavoritesNews(): Flow<List<ArticlesWithSetting>> {
        return newsListDao.getFavoritesNews()
    }

    override suspend fun saveArticleToDatabase(article: ArticleDbEntity) {
        newsListDao.insertArticle(article)
    }

    override suspend fun updateArticlesInDatabase(article: ArticleDbEntity) {
        newsListDao.updateArticle(article)
    }

    override suspend fun saveIsSelected(articleDbEntity: ArticleDbEntity) {
        newsListDao.insertArticleSetting(
            articleSetting = ArticleSettingsDbEntity(
                articleDbEntity.id,
                false
            )
        )
    }

    override suspend fun updateIsSelected(articleUi: ArticleUi) {
        newsListDao.updateArticleSetting(
            articleSetting = ArticleSettingsDbEntity(
                articleUi.id,
                articleUi.isSelected
            )
        )
    }
}