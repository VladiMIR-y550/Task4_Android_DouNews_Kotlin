package com.example.task4_android_dounews_kotlin.screens.news_list

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.task4_android_dounews_kotlin.model.NewsListRepository
import com.example.task4_android_dounews_kotlin.model.local.room.ArticleDbEntity
import com.example.task4_android_dounews_kotlin.screens.BaseViewModel
import com.example.task4_android_dounews_kotlin.utils.*
import com.example.task4_android_dounews_kotlin.utils.network.NetworkStatus
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

class NewsListViewModel(private val repository: NewsListRepository, application: Application) :
    BaseViewModel(application),
    AdapterActionListener {

    private val _articles = MutableLiveData<Result<List<ArticleDbEntity>>>()
    val articles: LiveData<Result<List<ArticleDbEntity>>> = _articles

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError(throwable)
    }
    private var articlesResult: Result<List<ArticleDbEntity>> = EmptyResult()
        set(value) {
            field = value
            notifyUpdates()
            Log.d("ARTICLE_RESULT", "ARTICLE_RESULT = ${articlesResult.javaClass.name}")
        }

    var baseSize: Int = 0
        private set

    init {
        subscribeOnNews()
    }

    fun updateNews() {
        getArticles(numberPagesLoaded = baseSize)
    }

    override fun loadPage(page: Int) {
        Log.d("TAG", "loadPage from pageAdapter = $page")
        getArticles(page = page)
    }

    fun subscribeOnNews() {
        articlesResult = PendingResult()
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            repository.getAllNews().shareIn(viewModelScope, SharingStarted.Eagerly, 1)
                .onEach { articles ->
                    Log.d("SubscribeOnNews", "Subscribe articles = $articles")
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
        _articles.postValue(articlesResult)
    }
}