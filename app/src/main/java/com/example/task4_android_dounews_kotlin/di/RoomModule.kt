package com.example.task4_android_dounews_kotlin.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.task4_android_dounews_kotlin.model.local.room.NewsRoomDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): NewsRoomDatabase {
        return Room.databaseBuilder(
            context,
            NewsRoomDatabase::class.java,
            "news_room_database"
        ).build()
    }
}