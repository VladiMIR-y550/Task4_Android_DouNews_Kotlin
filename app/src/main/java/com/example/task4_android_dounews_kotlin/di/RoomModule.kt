package com.example.task4_android_dounews_kotlin.di

import android.content.Context
import androidx.room.Room
import com.example.task4_android_dounews_kotlin.model.local.room.NewsRoomDatabase
import com.example.task4_android_dounews_kotlin.utils.USERS_ROOM_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext applicationContext: Context): NewsRoomDatabase {
        return Room.databaseBuilder(
            applicationContext,
            NewsRoomDatabase::class.java,
            USERS_ROOM_DATABASE
        ).build()
    }
}