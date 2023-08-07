package com.example.task4_android_dounews_kotlin.model.local.room

import androidx.room.*
import com.example.task4_android_dounews_kotlin.model.entities.ArticlesWithSetting
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsListDao {

    @Query("SELECT COUNT(*) FROM ARTICLES_TABLE ")
    fun countDbRows(): Int

    @Transaction
    @Query(
        "SELECT * FROM ${ArticleDbEntity.TABLE_NAME} " +
                "LEFT JOIN ${ArticleSettingsDbEntity.TABLE_SETTING} " +
                "ON articles_table.id = table_setting.setting_id " +
                "ORDER BY articles_table.date_long DESC"
    )
    fun getAllNewsFlow(): Flow<List<ArticlesWithSetting>>

    @Transaction
    @Query(
        "SELECT * FROM ${ArticleDbEntity.TABLE_NAME} " +
                "LEFT JOIN ${ArticleSettingsDbEntity.TABLE_SETTING} " +
                "ON articles_table.id = table_setting.setting_id " +
                "ORDER BY articles_table.date_long DESC"
    )
    fun getAllNews(): List<ArticlesWithSetting>

    @Transaction
    @Query("SELECT * FROM ${ArticleDbEntity.TABLE_NAME} " +
            "INNER JOIN ${ArticleSettingsDbEntity.TABLE_SETTING} " +
            "ON articles_table.id = table_setting.setting_id " +
            "AND table_setting.is_selected = true")
    fun getFavoritesNews(): Flow<List<ArticlesWithSetting>>

    @Insert(entity = ArticleDbEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(article: ArticleDbEntity)

    @Update(entity = ArticleDbEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun updateArticle(article: ArticleDbEntity)

    @Insert(entity = ArticleSettingsDbEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertArticleSetting(articleSetting: ArticleSettingsDbEntity)

    @Update(entity = ArticleSettingsDbEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun updateArticleSetting(articleSetting: ArticleSettingsDbEntity)
}