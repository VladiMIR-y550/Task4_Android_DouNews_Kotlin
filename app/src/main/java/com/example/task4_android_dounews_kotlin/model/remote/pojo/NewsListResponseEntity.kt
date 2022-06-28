package com.example.task4_android_dounews_kotlin.model.remote.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NewsListResponseEntity(
    @Expose
    @SerializedName("count") val count: Int,
    @Expose
    @SerializedName("next") val next: String,
    @Expose
    @SerializedName("previous") val previous: String,
    @Expose
    @SerializedName("results") val results: List<NewsResponseEntity>
)