package com.example.task4_android_dounews_kotlin.di

import com.example.task4_android_dounews_kotlin.model.remote.DouApi
import com.example.task4_android_dounews_kotlin.model.INewsListLocalDataSource
import com.example.task4_android_dounews_kotlin.model.INewsListRemoteDataSource
import com.example.task4_android_dounews_kotlin.model.NewsListRepository
import com.example.task4_android_dounews_kotlin.model.remote.RetrofitNewsListDataSource
import com.example.task4_android_dounews_kotlin.model.local.RoomNewsListDataSource
import com.example.task4_android_dounews_kotlin.model.local.room.NewsRoomDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideLocalDataSource(roomDataBase: NewsRoomDatabase): INewsListLocalDataSource =
        RoomNewsListDataSource(roomDataBase.getNewsListDao())

    @Provides
    @Singleton
    fun provideRemoteDataSource(douApi: DouApi): INewsListRemoteDataSource =
        RetrofitNewsListDataSource(douApi)

    @Provides
    @Singleton
    fun provideNewsListRepository(
        local: INewsListLocalDataSource,
        remote: INewsListRemoteDataSource
    ): NewsListRepository =
        NewsListRepository(local, remote)
}