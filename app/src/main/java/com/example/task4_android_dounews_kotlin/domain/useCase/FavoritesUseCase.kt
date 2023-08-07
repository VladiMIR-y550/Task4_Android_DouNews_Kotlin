package com.example.task4_android_dounews_kotlin.domain.useCase

import com.example.task4_android_dounews_kotlin.model.NewsListRepository
import com.example.task4_android_dounews_kotlin.model.entities.ArticleUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class FavoritesUseCase(
    private val repository: NewsListRepository
) {

    suspend fun subscribeFavoriteNews(): Flow<List<ArticleUi>> {
        return repository.getFavoritesNews()
    }

    suspend fun changeFavoritesStateArticle(articleUi: ArticleUi) {
        withContext(Dispatchers.IO) {
            repository.articleIsSelected(article = articleUi.copy(isSelected = !articleUi.isSelected))
        }
    }
}