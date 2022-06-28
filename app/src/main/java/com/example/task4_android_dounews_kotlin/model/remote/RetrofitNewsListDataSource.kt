package com.example.task4_android_dounews_kotlin.model.remote

import com.example.task4_android_dounews_kotlin.model.INewsListRemoteDataSource
import com.example.task4_android_dounews_kotlin.model.remote.pojo.NewsListResponseEntity
import retrofit2.Response
import javax.inject.Inject

class RetrofitNewsListDataSource @Inject constructor(private val douApi: DouApi) : INewsListRemoteDataSource {

    override suspend fun downloadNewsList(
        numberPagesLoaded: Int,
        page: Int
    ): Response<NewsListResponseEntity> =
        douApi.downloadNews(numberPagesLoaded, page)
}