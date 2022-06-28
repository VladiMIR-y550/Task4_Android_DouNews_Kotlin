package com.example.task4_android_dounews_kotlin.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.task4_android_dounews_kotlin.utils.network.NetworkStatus
import com.example.task4_android_dounews_kotlin.utils.network.networkStatus
import kotlinx.coroutines.flow.*

abstract class BaseViewModel(application: Application): AndroidViewModel(application){
    val changedNetworkStatus = application.applicationContext.networkStatus
        .shareIn(viewModelScope, SharingStarted.Eagerly, 1)

    var networkStatus: NetworkStatus = NetworkStatus.Unavailable
        private set

    init {
        changedNetworkStatus.onEach { status ->
            networkStatus = status }.launchIn(viewModelScope)
    }
}