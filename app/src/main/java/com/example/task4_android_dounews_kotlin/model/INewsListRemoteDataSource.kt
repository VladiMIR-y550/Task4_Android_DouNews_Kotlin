package com.example.task4_android_dounews_kotlin.model

import com.example.task4_android_dounews_kotlin.model.remote.pojo.NewsListResponseEntity
import retrofit2.Response

interface INewsListRemoteDataSource {
    suspend fun downloadNewsList(numberPagesLoaded: Int, page: Int): Response<NewsListResponseEntity>
}