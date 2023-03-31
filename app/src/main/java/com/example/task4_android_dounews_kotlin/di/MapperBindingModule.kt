package com.example.task4_android_dounews_kotlin.di

import com.example.task4_android_dounews_kotlin.domain.modelsUi.ArticleUi
import com.example.task4_android_dounews_kotlin.model.local.room.ArticleDbEntity
import com.example.task4_android_dounews_kotlin.model.mappers.ItemMapper
import com.example.task4_android_dounews_kotlin.model.mappers.NewsMapper
import com.example.task4_android_dounews_kotlin.model.remote.pojo.NewsResponseEntity
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface MapperBindingModule {
    @Binds
    fun bindItemMapper(mapperImpl: ItemMapper): NewsMapper<ArticleDbEntity, ArticleUi, NewsResponseEntity>
}