package com.example.task4_android_dounews_kotlin.model.local.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ArticleDbEntity::class, ArticleSettingsDbEntity::class],
    version = 4,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(
            from = 1,
            to = 2,
            spec = AutoMigrationSpec1to2::class
        ),
        AutoMigration(
            from = 2,
            to = 3,
            spec = AutoMigrationSpec2to3::class
        ),
        AutoMigration(
            from = 3,
            to = 4
        )
    ]
)
abstract class NewsRoomDatabase : RoomDatabase() {
    abstract fun getNewsListDao(): NewsListDao
}