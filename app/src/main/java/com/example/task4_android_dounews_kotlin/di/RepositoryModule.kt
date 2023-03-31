package com.example.task4_android_dounews_kotlin.di

import com.example.task4_android_dounews_kotlin.domain.modelsUi.ArticleUi
import com.example.task4_android_dounews_kotlin.model.remote.DouApi
import com.example.task4_android_dounews_kotlin.model.local.NewsListLocalDataSource
import com.example.task4_android_dounews_kotlin.model.remote.NewsListRemoteDataSource
import com.example.task4_android_dounews_kotlin.model.NewsListRepositoryImpl
import com.example.task4_android_dounews_kotlin.model.remote.NewsListRemoteDataSourceImpl
import com.example.task4_android_dounews_kotlin.model.local.NewsListLocalDataSourceImpl
import com.example.task4_android_dounews_kotlin.model.local.room.ArticleDbEntity
import com.example.task4_android_dounews_kotlin.model.local.room.NewsRoomDatabase
import com.example.task4_android_dounews_kotlin.model.mappers.NewsMapper
import com.example.task4_android_dounews_kotlin.model.remote.pojo.NewsResponseEntity
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
        mapper: NewsMapper<ArticleDbEntity, ArticleUi, NewsResponseEntity>
    ): NewsListRepositoryImpl =
        NewsListRepositoryImpl(local, remote, mapper)
}