package com.example.task4_android_dounews_kotlin.utils

import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.task4_android_dounews_kotlin.R
import com.example.task4_android_dounews_kotlin.utils.network.NetworkStatus

@BindingAdapter(value = ["app.pageUrl"], requireAll = true)
fun loadUrl(view: WebView, url: String) {
    view.loadUrl(url)
}

@BindingAdapter(value = ["app:networkStatus"])
fun networkStatus(view: View, networkStatus: NetworkStatus) {
    when (networkStatus) {
        NetworkStatus.Unavailable -> {
            view.visibility = View.VISIBLE
        }
        NetworkStatus.Available -> {
            view.visibility = View.GONE
        }
    }
}

@BindingAdapter(value = ["app.isSelected"], requireAll = true)
fun articleIsSelect(view: ImageView, isSelected: Boolean) {
    if (isSelected) view.setImageResource(R.drawable.ic_bookmark) else view.setImageResource(R.drawable.ic_bookmark_border)
}