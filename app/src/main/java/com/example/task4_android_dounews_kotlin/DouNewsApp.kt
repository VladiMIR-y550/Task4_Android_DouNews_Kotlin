package com.example.task4_android_dounews_kotlin

import android.app.Application
import android.content.Context
import com.example.task4_android_dounews_kotlin.di.AppComponent
import com.example.task4_android_dounews_kotlin.di.ContextModule
import com.example.task4_android_dounews_kotlin.di.DaggerAppComponent
import com.example.task4_android_dounews_kotlin.di.RemoteModule

class DouNewsApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .contextModule(ContextModule(applicationContext))
            .remoteModule(RemoteModule())
            .build()
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is DouNewsApp -> appComponent
        else -> this.applicationContext.appComponent
    }