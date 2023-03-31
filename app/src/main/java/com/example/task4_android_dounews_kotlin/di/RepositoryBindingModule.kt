package com.example.task4_android_dounews_kotlin.di

import com.example.task4_android_dounews_kotlin.model.NewsListRepository
import com.example.task4_android_dounews_kotlin.model.NewsListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryBindingModule {

    @Binds
    fun bindNewsRepositoryImplToNewsRepository(
        newsListRepositoryImpl: NewsListRepositoryImpl
    ): NewsListRepository
}