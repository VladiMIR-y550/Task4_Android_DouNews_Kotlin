package com.example.task4_android_dounews_kotlin.model

import com.example.task4_android_dounews_kotlin.model.entities.ArticleUi
import com.example.task4_android_dounews_kotlin.model.local.NewsListLocalDataSource
import com.example.task4_android_dounews_kotlin.model.local.room.ArticleDbEntity
import com.example.task4_android_dounews_kotlin.model.remote.NewsListRemoteDataSource
import com.example.task4_android_dounews_kotlin.model.remote.pojo.NewsResponseEntity
import com.example.task4_android_dounews_kotlin.utils.AMOUNT_DOWNLOAD_PAGES
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsListRepositoryImpl @Inject constructor(
    private val localDataSource: NewsListLocalDataSource,
    private val remoteDataSource: NewsListRemoteDataSource,
) : NewsListRepository {

    override suspend fun getAllNews(): Flow<List<ArticleUi>> {
        if (localDataSource.countDbRows() == 0) {
            downloadNews(numberPagesLoaded = AMOUNT_DOWNLOAD_PAGES, page = 0)
        }
        return localDataSource.getAllArticlesFlow().map { news ->
            news.map { article ->
                article.toArticleUi()
            }
        }
    }

    override suspend fun getFavoritesNews(): Flow<List<ArticleUi>> {
        return localDataSource.getFavoritesNews().map { articles ->
            articles.map { article ->
                article.toArticleUi()
            }
        }
    }

    override suspend fun downloadNews(numberPagesLoaded: Int, page: Int) {
        withContext(Dispatchers.IO) {
            val allNews = localDataSource.getAllArticles()
            val response = remoteDataSource.downloadNewsList(numberPagesLoaded, page)
            if (response.isSuccessful) {
                response.body()?.also { news ->
                    news.results.forEach { responseArticle ->
                        allNews.find { it.articleDbEntity.id == responseArticle.id }
                            ?.let {
                                localDataSource.updateArticlesInDatabase(
                                    ArticleDbEntity.fromNewsResponseEntity(
                                        responseArticle
                                    )
                                )
                            } ?: savedNewArticle(responseArticle)
                    }
                }
            }
        }
    }

    private suspend fun savedNewArticle(responseArticle: NewsResponseEntity) {
        val article = ArticleDbEntity.fromNewsResponseEntity(responseArticle)
        localDataSource.saveArticleToDatabase(article)
        localDataSource.saveIsSelected(article)
    }

    override suspend fun articleIsSelected(article: ArticleUi) {
        withContext(Dispatchers.IO) {
            localDataSource.updateIsSelected(article)
        }
    }
}