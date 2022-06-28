package com.example.task4_android_dounews_kotlin.di

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ContextModule(val context: Context) {

    @Provides
    fun provideApplicationContext(): Context = context.applicationContext
}