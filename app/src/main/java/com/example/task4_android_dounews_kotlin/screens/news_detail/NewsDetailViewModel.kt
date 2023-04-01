package com.example.task4_android_dounews_kotlin.screens.news_detail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.task4_android_dounews_kotlin.domain.modelsUi.WebPageUi
import com.example.task4_android_dounews_kotlin.screens.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    @ApplicationContext applicationContext: Context,
//    stateHandle: SavedStateHandle
) : BaseViewModel(applicationContext) {

    private val webPageStateInternal = MutableLiveData(
//        stateHandle.get<String>(PAGE_URL_KEY)?.let {
//        WebPageUi(
//            selectedNews = it,
//            networkStatus = networkStatus
//        )
//    } ?:
    WebPageUi()
    )
    val webPageState: LiveData<WebPageUi> = webPageStateInternal

    fun downloadDetailedNews(articleUrl: String) {
        webPageStateInternal.value = webPageStateInternal.value?.copy(selectedNews = articleUrl)
//        selectedNews = articleUrl
    }

    fun saveScrollPosition(scrollPosition: Int) {
        webPageStateInternal.value =
            webPageStateInternal.value?.copy(scrollPagePosition = scrollPosition)
//        scrollPositionInternal.value = scrollPosition
    }

    fun updateIsProgress(isProgress: Boolean) {
        webPageStateInternal.value = webPageStateInternal.value?.copy(isProgress = isProgress)
//        progressStateInternal.value = isProgress
    }
}