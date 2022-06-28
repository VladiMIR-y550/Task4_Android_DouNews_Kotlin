package com.example.task4_android_dounews_kotlin.model.remote

import com.example.task4_android_dounews_kotlin.model.remote.pojo.NewsListResponseEntity
import com.example.task4_android_dounews_kotlin.utils.AMOUNT_DOWNLOAD_PAGES
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DouApi {
    @GET("articles")
    suspend fun downloadNews(
        @Query("limit") amountDownloadPages: Int = AMOUNT_DOWNLOAD_PAGES,
        @Query("offset") page: Int = 0
    ): Response<NewsListResponseEntity>
}