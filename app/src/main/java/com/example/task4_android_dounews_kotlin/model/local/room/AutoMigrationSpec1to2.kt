package com.example.task4_android_dounews_kotlin.model.local.room

import androidx.room.RenameColumn
import androidx.room.migration.AutoMigrationSpec
import com.example.task4_android_dounews_kotlin.model.local.room.ArticleDbEntity.Companion.TABLE_NAME

@RenameColumn.Entries(
    value = [
        RenameColumn(tableName = TABLE_NAME, fromColumnName = "dateLong", toColumnName = "date_long"),
        RenameColumn(tableName = TABLE_NAME, fromColumnName = "authorUrl", toColumnName = "author_url"),
        RenameColumn(tableName = TABLE_NAME, fromColumnName = "imgSmall2x", toColumnName = "img_small_2x"),
        RenameColumn(tableName = TABLE_NAME, fromColumnName = "imgSmall", toColumnName = "img_small"),
        RenameColumn(tableName = TABLE_NAME, fromColumnName = "imgBig2x", toColumnName = "img_big_2x"),
        RenameColumn(tableName = TABLE_NAME, fromColumnName = "imgBig", toColumnName = "img_big"),
        RenameColumn(tableName = TABLE_NAME, fromColumnName = "commentsCount", toColumnName = "comments_count"),
        RenameColumn(tableName = TABLE_NAME, fromColumnName = "categoryUrl", toColumnName = "category_url"),
    ]
)
class AutoMigrationSpec1to2 : AutoMigrationSpec