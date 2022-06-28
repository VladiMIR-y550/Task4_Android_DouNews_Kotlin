package com.example.task4_android_dounews_kotlin.di

import com.example.task4_android_dounews_kotlin.screens.news_list.NewsListViewModel
import com.example.task4_android_dounews_kotlin.utils.DouViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(newsListViewModel: NewsListViewModel)
    fun inject(viewModelFactory: DouViewModelFactory)
}