package com.example.task4_android_dounews_kotlin.utils.network

sealed class NetworkStatus {
    object Available : NetworkStatus()
    object Unavailable : NetworkStatus()
}
