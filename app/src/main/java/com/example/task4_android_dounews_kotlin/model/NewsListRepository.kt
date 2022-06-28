package com.example.task4_android_dounews_kotlin.model

import com.example.task4_android_dounews_kotlin.model.local.room.ArticleDbEntity
import com.example.task4_android_dounews_kotlin.utils.AMOUNT_DOWNLOAD_PAGES
import com.example.task4_android_dounews_kotlin.utils.toArticleDbEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsListRepository @Inject constructor(
    private val localDataSource: INewsListLocalDataSource,
    private val remoteDataSource: INewsListRemoteDataSource
) {

    suspend fun getAllNews(): Flow<List<ArticleDbEntity>> {
        if (localDataSource.countDbRows() == 0) {
            downloadNews()
        }
        return localDataSource.getAllArticles()
    }

    suspend fun downloadNews(numberPagesLoaded: Int = AMOUNT_DOWNLOAD_PAGES, page: Int = 0) {
        val response = remoteDataSource.downloadNewsList(numberPagesLoaded, page)
        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                response.body()!!.results.forEach {
                    val article = this@NewsListRepository.toArticleDbEntity(it)
                    localDataSource.saveArticleToDatabase(article)
                }
            }
        }
    }
}