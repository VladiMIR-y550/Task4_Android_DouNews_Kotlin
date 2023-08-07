package com.example.task4_android_dounews_kotlin.screens.news_selected

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task4_android_dounews_kotlin.domain.useCase.FavoritesUseCase
import com.example.task4_android_dounews_kotlin.model.entities.ArticleUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesUseCase: FavoritesUseCase
) : ViewModel() {

    private var favoriteNewsMutableState = MutableStateFlow<List<ArticleUi>>(listOf())
    val favoriteNewsState: StateFlow<List<ArticleUi>> = favoriteNewsMutableState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            favoritesUseCase.subscribeFavoriteNews().collect { favoriteNews ->
                favoriteNewsMutableState.emit(favoriteNews)
            }
        }
    }

    fun updateIsFavorites(articleUi: ArticleUi) {
        viewModelScope.launch(Dispatchers.IO) {
            favoritesUseCase.changeFavoritesStateArticle(articleUi = articleUi)
        }
    }
}