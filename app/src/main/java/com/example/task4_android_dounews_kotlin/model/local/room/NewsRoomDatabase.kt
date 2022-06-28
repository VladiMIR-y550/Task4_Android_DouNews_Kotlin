package com.example.task4_android_dounews_kotlin.model.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ArticleDbEntity::class], version = 1, exportSchema = true)
abstract class NewsRoomDatabase : RoomDatabase() {
    abstract fun getNewsListDao(): NewsListDao
}