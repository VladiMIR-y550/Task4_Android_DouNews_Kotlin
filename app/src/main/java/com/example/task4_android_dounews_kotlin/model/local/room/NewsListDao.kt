package com.example.task4_android_dounews_kotlin.model.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsListDao {

    @Query("SELECT COUNT(*) FROM ARTICLES_TABLE")
    fun countDbRows(): Int

    @Query("SELECT * FROM ARTICLES_TABLE ORDER BY dateLong DESC")
    fun getAllArticles(): Flow<List<ArticleDbEntity>>

    @Insert(entity = ArticleDbEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: ArticleDbEntity)

    @Update(entity = ArticleDbEntity::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(article: ArticleDbEntity)
}