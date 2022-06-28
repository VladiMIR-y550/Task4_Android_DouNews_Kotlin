package com.example.task4_android_dounews_kotlin.screens.news_detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.task4_android_dounews_kotlin.screens.BaseViewModel

class NewsDetailViewModel(application: Application) : BaseViewModel(application) {
    private val _scrollPosition = MutableLiveData<Int>()
    val scrollPosition: LiveData<Int> = _scrollPosition

    var selectedNews: String? = null
        private set

    fun downloadDetailedNews(articleUrl: String?) {
        selectedNews = articleUrl
    }

    fun saveScrollPosition(scrollPosition: Int) {
        _scrollPosition.value = scrollPosition
        Log.d("saveScrollPosition", "ScrollY = $scrollPosition")
    }
}