package com.example.task4_android_dounews_kotlin.screens.news_list

import androidx.recyclerview.widget.DiffUtil
import com.example.task4_android_dounews_kotlin.model.local.room.ArticleDbEntity

class ArticlesDiffCallback(
    private val oldList: List<ArticleDbEntity>,
    private val newList: List<ArticleDbEntity>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int =newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldArticle = oldList[oldItemPosition]
        val newArticle = newList[newItemPosition]
        return oldArticle.id == newArticle.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldArticle = oldList[oldItemPosition]
        val newArticle = newList[newItemPosition]
        return oldArticle == newArticle
    }

}