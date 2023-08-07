package com.example.task4_android_dounews_kotlin.di

import com.example.task4_android_dounews_kotlin.domain.useCase.FavoritesUseCase
import com.example.task4_android_dounews_kotlin.model.NewsListRepository
import com.example.task4_android_dounews_kotlin.model.local.NewsListLocalDataSource
import com.example.task4_android_dounews_kotlin.model.local.NewsListLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {

    @Provides
    @Singleton
    fun provideFavoriteUseCase(repository: NewsListRepository): FavoritesUseCase =
        FavoritesUseCase(repository = repository)
}