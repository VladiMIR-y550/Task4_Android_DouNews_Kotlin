package com.example.task4_android_dounews_kotlin.screens

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task4_android_dounews_kotlin.utils.network.NetworkStatus
import com.example.task4_android_dounews_kotlin.utils.network.networkStatus
import kotlinx.coroutines.flow.*

abstract class BaseViewModel (applicationContext: Context): ViewModel(){
    val changedNetworkStatus = applicationContext.networkStatus
        .shareIn(viewModelScope, SharingStarted.Eagerly, 1)

    var networkStatus: NetworkStatus = NetworkStatus.Unavailable
        private set

    init {
        changedNetworkStatus.onEach { status ->
            networkStatus = status }.launchIn(viewModelScope)
    }
}