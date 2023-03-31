package com.example.task4_android_dounews_kotlin.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun convertStringToDate(dataString: String): Date =
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dataString) as Date

@SuppressLint("SimpleDateFormat")
fun refactorPublished(date: Date): String =
    SimpleDateFormat("d MMMM yyyy H:mm").format(date)

fun formatDateToLong(date: Date): Long {
    return date.time
}