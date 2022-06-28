package com.example.task4_android_dounews_kotlin.utils

import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.task4_android_dounews_kotlin.R
import com.example.task4_android_dounews_kotlin.utils.network.NetworkStatus

@BindingAdapter(value = ["app.pageUrl", "app.withInternet"], requireAll = true)
fun loadUrl(view: WebView, url: String, networkStatus: NetworkStatus) {
    when (networkStatus) {
        NetworkStatus.Available -> url.let { view.loadUrl(url) }
        else -> {}
    }
}

@BindingAdapter(value = ["app:networkStatus"])
fun networkStatus(view: View, networkStatus: NetworkStatus) {
    when (networkStatus) {
        NetworkStatus.Unavailable -> {
            view.visibility = View.VISIBLE
        }
        else -> {
            view.visibility = View.GONE
        }
    }
}