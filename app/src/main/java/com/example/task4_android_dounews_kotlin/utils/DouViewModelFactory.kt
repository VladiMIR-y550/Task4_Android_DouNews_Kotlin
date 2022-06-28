package com.example.task4_android_dounews_kotlin.utils

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.task4_android_dounews_kotlin.DouNewsApp
import com.example.task4_android_dounews_kotlin.appComponent
import com.example.task4_android_dounews_kotlin.model.NewsListRepository
import com.example.task4_android_dounews_kotlin.screens.news_detail.NewsDetailViewModel
import com.example.task4_android_dounews_kotlin.screens.news_list.NewsListViewModel
import javax.inject.Inject

class DouViewModelFactory(val application: Application): ViewModelProvider.AndroidViewModelFactory(application) {
    @Inject
    lateinit var repository: NewsListRepository

    init {
        application.appComponent.inject(this)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            NewsListViewModel::class.java -> {
                NewsListViewModel(repository, application)
            }
            NewsDetailViewModel::class.java -> {
                NewsDetailViewModel(application)
            }
            else -> {
                throw IllegalStateException("Unknown view model class")
            }
        }
        return viewModel as T
    }
}

fun Fragment.factory() = DouViewModelFactory(context!!.applicationContext as DouNewsApp)
