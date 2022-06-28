package com.example.task4_android_dounews_kotlin.screens.news_list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task4_android_dounews_kotlin.R
import com.example.task4_android_dounews_kotlin.databinding.LayoutArticleCardBinding
import com.example.task4_android_dounews_kotlin.model.local.room.ArticleDbEntity
import com.example.task4_android_dounews_kotlin.utils.PRELOAD_ARTICLES

interface AdapterActionListener {
    fun loadPage(page: Int)
}

class PagingAdapter(
    private val adapterActionListener: AdapterActionListener
) :
    RecyclerView.Adapter<PagingAdapter.NewsViewHolder>() {

    var articlesList: List<ArticleDbEntity> = emptyList()
        set(newValue) {
            val diffCallback = ArticlesDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val articleCardBinding = LayoutArticleCardBinding.inflate(inflater, parent, false)

        return NewsViewHolder(binding = articleCardBinding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articlesList[position]
        holder.bind(article)
        Log.d("PagingAdapter", "onBindViewHolder = $position")

        if (position == articlesList.size - PRELOAD_ARTICLES) {
            adapterActionListener.loadPage(articlesList.size)
        }

        holder.itemView.setOnClickListener {
            val action =
                NewsListFragmentDirections.actionNewsListFragmentToDetailNewsFragment(article.url)
            holder.itemView.findNavController().navigate(action)

        }
    }

    override fun getItemCount() = articlesList.size

    class NewsViewHolder(
        val binding: LayoutArticleCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(articleDbEntity: ArticleDbEntity) {
            binding.article = articleDbEntity

            Glide.with(binding.ivTitle.context)
                .load(articleDbEntity.imgBig2x)
                .placeholder(R.drawable.ic_preview)
                .error(R.drawable.ic_error_outline)
                .into(binding.ivTitle)

            Glide.with(binding.ivAuthor.context)
                .load(articleDbEntity.imgSmall)
                .circleCrop()
                .placeholder(R.drawable.ic_account)
                .error(R.drawable.ic_account)
                .into(binding.ivAuthor)
        }
    }
}