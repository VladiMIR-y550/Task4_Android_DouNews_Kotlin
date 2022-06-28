package com.example.task4_android_dounews_kotlin.di

import android.content.Context
import com.example.task4_android_dounews_kotlin.DouNewsApp
import com.example.task4_android_dounews_kotlin.utils.DouViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelFactoryModule {
    @Provides
    @Singleton
    fun provideDouViewModelFactory(
        context: Context
    ): DouViewModelFactory = DouViewModelFactory(context.applicationContext as DouNewsApp)
}