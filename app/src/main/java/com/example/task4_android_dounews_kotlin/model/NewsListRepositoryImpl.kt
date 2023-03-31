package com.example.task4_android_dounews_kotlin.model

import com.example.task4_android_dounews_kotlin.domain.modelsUi.ArticleUi
import com.example.task4_android_dounews_kotlin.model.local.NewsListLocalDataSource
import com.example.task4_android_dounews_kotlin.model.local.room.ArticleDbEntity
import com.example.task4_android_dounews_kotlin.model.mappers.NewsMapper
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
    private val mapper: NewsMapper<ArticleDbEntity, ArticleUi, NewsResponseEntity>
) : NewsListRepository {

    override suspend fun getAllNews(): Flow<List<ArticleUi>> {
        if (localDataSource.countDbRows() == 0) {
            downloadNews(numberPagesLoaded = AMOUNT_DOWNLOAD_PAGES, page = 0)
        }
        return localDataSource.getAllArticles().map {
            mapper.toUiEntityList(it)
        }
    }

    override suspend fun downloadNews(numberPagesLoaded: Int, page: Int) {
        val response = remoteDataSource.downloadNewsList(numberPagesLoaded, page)
        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                response.body()?.also { news ->
                    news.results.forEach {
                        localDataSource.saveArticleToDatabase(mapper.fromResponseEntity(it))
                    }
                }
            }
        }
    }
}