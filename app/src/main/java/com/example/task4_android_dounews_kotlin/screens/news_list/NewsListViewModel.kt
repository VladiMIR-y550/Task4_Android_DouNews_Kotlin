package com.example.task4_android_dounews_kotlin.screens.news_list

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.task4_android_dounews_kotlin.domain.useCase.FavoritesUseCase
import com.example.task4_android_dounews_kotlin.model.entities.ArticleUi
import com.example.task4_android_dounews_kotlin.model.NewsListRepository
import com.example.task4_android_dounews_kotlin.screens.BaseViewModel
import com.example.task4_android_dounews_kotlin.utils.*
import com.example.task4_android_dounews_kotlin.utils.network.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val repository: NewsListRepository,
    private val favoritesUseCase: FavoritesUseCase,
    @ApplicationContext applicationContext: Context
) :
    BaseViewModel(applicationContext) {

    private val articlesStateInternal = MutableLiveData<Result<List<ArticleUi>>>()
    val articlesState: LiveData<Result<List<ArticleUi>>> = articlesStateInternal

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError(throwable)
    }
    private var articlesResult: Result<List<ArticleUi>> = EmptyResult()
        set(value) {
            field = value
            notifyUpdates()
        }

    var baseSize: Int = 0
        private set

    init {
        subscribeOnNews()
    }

    fun updateNews() {
        getArticles(numberPagesLoaded = baseSize)
    }

    fun loadPage(page: Int) {
        getArticles(page = page)
    }

    fun subscribeOnNews() {
        articlesResult = PendingResult()
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            repository.getAllNews().shareIn(viewModelScope, SharingStarted.Eagerly, 1)
                .onEach { articles ->
                    baseSize = articles.size
                    articlesResult = if (articles.isEmpty()) {
                        EmptyResult()
                    } else {
                        SuccessResult(articles)
                    }
                    notifyUpdates()
                }.launchIn(viewModelScope)
        }
    }

    fun newsIsSelected(news: ArticleUi) {
        viewModelScope.launch(Dispatchers.IO) {
            favoritesUseCase.changeFavoritesStateArticle(articleUi = news)
        }
    }

    private fun getArticles(numberPagesLoaded: Int = AMOUNT_DOWNLOAD_PAGES, page: Int = 0) {
        when (networkStatus) {
            NetworkStatus.Available -> {
                articlesResult = PendingResult()
                viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
                    repository.downloadNews(numberPagesLoaded, page)
                }
            }
            NetworkStatus.Unavailable -> {
                articlesResult =
                    ErrorResult(InternetLostException("Internet problems, please check your connection!"))
            }
        }
    }

    private fun onError(errorBody: Throwable) {
        articlesResult = ErrorResult(errorBody)
    }

    private fun notifyUpdates() {
        articlesStateInternal.postValue(articlesResult)
    }

//    fun showFavorites() {
//        articlesResult = PendingResult()
//        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
//            repository.getFavoritesNews().collect { favorites ->
//                articlesResult =
//                    if (favorites.isEmpty()) EmptyResult() else SuccessResult(favorites)
//            }
//        }
//    }
}