package com.example.task4_android_dounews_kotlin.screens.news_detail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.task4_android_dounews_kotlin.screens.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    @ApplicationContext applicationContext: Context
) : BaseViewModel(applicationContext) {
    private val scrollPositionInternal = MutableLiveData<Int>()
    val scrollPosition: LiveData<Int> = scrollPositionInternal

    var selectedNews: String? = null
        private set

    fun downloadDetailedNews(articleUrl: String?) {
        selectedNews = articleUrl
    }

    fun saveScrollPosition(scrollPosition: Int) {
        scrollPositionInternal.value = scrollPosition
    }
}