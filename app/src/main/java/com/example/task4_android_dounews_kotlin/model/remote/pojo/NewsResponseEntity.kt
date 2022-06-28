package com.example.task4_android_dounews_kotlin.model.remote.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NewsResponseEntity(
    @Expose
    @SerializedName("id") val id: Long,
    @Expose
    @SerializedName("title") val title: String,
    @Expose
    @SerializedName("url") val url: String,
    @Expose
    @SerializedName("category") val category: String,
    @Expose
    @SerializedName("category_url") val categoryUrl: String,
    @Expose
    @SerializedName("announcement") val announcement: String,
    @Expose
    @SerializedName("tags") val tags: String,
    @Expose
    @SerializedName("pageviews") val pageviews: Int,
    @Expose
    @SerializedName("commentsCount") val commentsCount: Int,
    @Expose
    @SerializedName("imgBig") val imgBig: String,
    @Expose
    @SerializedName("img_big_2x") val imgBig2x: String,
    @Expose
    @SerializedName("img_small") val imgSmall: String,
    @Expose
    @SerializedName("img_small_2x") val imgSmall2x: String,
    @Expose
    @SerializedName("author_name") val authorName: String,
    @Expose
    @SerializedName("author_url") val authorUrl: String,
    @Expose
    @SerializedName("published") val published: String
)