package com.example.task4_android_dounews_kotlin.di

import com.example.task4_android_dounews_kotlin.model.remote.DouApi
import com.example.task4_android_dounews_kotlin.model.local.NewsListLocalDataSource
import com.example.task4_android_dounews_kotlin.model.remote.NewsListRemoteDataSource
import com.example.task4_android_dounews_kotlin.model.NewsListRepositoryImpl
import com.example.task4_android_dounews_kotlin.model.remote.NewsListRemoteDataSourceImpl
import com.example.task4_android_dounews_kotlin.model.local.NewsListLocalDataSourceImpl
import com.example.task4_android_dounews_kotlin.model.local.room.NewsRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideLocalDataSource(roomDataBase: NewsRoomDatabase): NewsListLocalDataSource =
        NewsListLocalDataSourceImpl(roomDataBase.getNewsListDao())

    @Provides
    @Singleton
    fun provideRemoteDataSource(douApi: DouApi): NewsListRemoteDataSource =
        NewsListRemoteDataSourceImpl(douApi)

    @Provides
    @Singleton
    fun provideNewsListRepository(
        local: NewsListLocalDataSource,
        remote: NewsListRemoteDataSource,
    ): NewsListRepositoryImpl =
        NewsListRepositoryImpl(local, remote)
}