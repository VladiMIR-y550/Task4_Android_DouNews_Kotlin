package com.example.task4_android_dounews_kotlin.screens

import android.content.Context
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext applicationContext: Context
) : BaseViewModel(applicationContext)