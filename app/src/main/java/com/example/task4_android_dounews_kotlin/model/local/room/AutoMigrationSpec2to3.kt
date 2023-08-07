package com.example.task4_android_dounews_kotlin.model.local.room

import androidx.room.DeleteColumn
import androidx.room.migration.AutoMigrationSpec

@DeleteColumn(tableName = ArticleDbEntity.TABLE_NAME, columnName = "is_selected")
class AutoMigrationSpec2to3: AutoMigrationSpec