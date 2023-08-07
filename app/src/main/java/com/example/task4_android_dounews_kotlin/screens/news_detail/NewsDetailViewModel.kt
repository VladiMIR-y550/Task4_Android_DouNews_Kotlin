package com.example.task4_android_dounews_kotlin.screens.news_detail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.task4_android_dounews_kotlin.model.entities.WebPageUi
import com.example.task4_android_dounews_kotlin.screens.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    @ApplicationContext applicationContext: Context,
) : BaseViewModel(applicationContext) {

    private val webPageInternal = MutableStateFlow(
        WebPageUi()
    )
    val webPage: StateFlow<WebPageUi> = webPageInternal.asStateFlow()

    fun downloadDetailedNews(articleUrl: String) {
        webPageInternal.value = webPage.value.copy(selectedNews = articleUrl)
    }

    fun saveScrollPosition(scrollPosition: Int) {
        webPageInternal.value = webPage.value.copy(scrollPagePosition = scrollPosition)
    }

    fun updateIsProgress(isProgress: Boolean) {
        webPageInternal.value = webPage.value.copy(isProgress = isProgress)
    }
}