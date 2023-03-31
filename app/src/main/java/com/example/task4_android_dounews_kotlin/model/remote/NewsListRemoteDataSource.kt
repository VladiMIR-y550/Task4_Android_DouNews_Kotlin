package com.example.task4_android_dounews_kotlin.model.remote

import com.example.task4_android_dounews_kotlin.model.remote.pojo.NewsListResponseEntity
import retrofit2.Response

interface NewsListRemoteDataSource {
    suspend fun downloadNewsList(numberPagesLoaded: Int, page: Int): Response<NewsListResponseEntity>
}