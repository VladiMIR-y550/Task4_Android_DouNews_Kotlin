package com.example.task4_android_dounews_kotlin.screens.news_list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task4_android_dounews_kotlin.R
import com.example.task4_android_dounews_kotlin.databinding.ItemArticleBinding
import com.example.task4_android_dounews_kotlin.model.entities.ArticleUi
import com.example.task4_android_dounews_kotlin.model.entities.SavedChanged
import com.example.task4_android_dounews_kotlin.utils.PRELOAD_ARTICLES

class NewsListAdapter(
    private val loadPage: (page: Int) -> Unit = {},
    private val onNewsDetails: (article: ArticleUi) -> Unit,
    private val onNewsFavoriteListener: (article: ArticleUi) -> Unit,
) : ListAdapter<ArticleUi, ArticleViewHolder>(ArticleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ArticleViewHolder(
            ItemArticleBinding.inflate(inflater, parent, false),
            onNewsDetails = onNewsDetails,
            onNewsFavoriteListener = onNewsFavoriteListener
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = currentList[position]
        holder.bind(article)

        if (position == currentList.size - PRELOAD_ARTICLES) {
            loadPage(currentList.size)
        }
    }

    override fun onBindViewHolder(
        holder: ArticleViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.bind(currentList[position], payloads)
        }
    }
}

class ArticleViewHolder(
    private val binding: ItemArticleBinding,
    private val onNewsDetails: (article: ArticleUi) -> Unit,
    private val onNewsFavoriteListener: (news: ArticleUi) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: ArticleUi, payloads: List<Any>) {
        binding.ivSelectedNews.setOnClickListener { onNewsFavoriteListener(data) }
        payloads.find { it is SavedChanged }?.also { payload ->
            (payload as? SavedChanged)?.let { savedChanged ->
                binding.ivSelectedNews.setChecked(savedChanged.isSaved)
            }
        }
    }

    fun bind(article: ArticleUi) {
        binding.article = article

        binding.ivSelectedNews.setOnClickListener {
            onNewsFavoriteListener(article)
        }

        itemView.setOnClickListener {
            onNewsDetails(article)
        }
        Glide.with(binding.ivTitle.context)
            .load(article.imgBig2x)
            .placeholder(R.drawable.ic_preview)
            .error(R.drawable.ic_error_outline)
            .into(binding.ivTitle)

        Glide.with(binding.ivAuthor.context)
            .load(article.imgSmall)
            .circleCrop()
            .placeholder(R.drawable.ic_account)
            .error(R.drawable.ic_account)
            .into(binding.ivAuthor)
    }

    private fun ImageView.setChecked(isChecked: Boolean) {
        val icon = when (isChecked) {
            true -> R.drawable.ic_bookmark
            false -> R.drawable.ic_bookmark_border
        }
        setImageResource(icon)
    }
}

class ArticleDiffCallback : DiffUtil.ItemCallback<ArticleUi>() {
    override fun areItemsTheSame(oldItem: ArticleUi, newItem: ArticleUi): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ArticleUi, newItem: ArticleUi): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: ArticleUi, newItem: ArticleUi): Any? {
        if (oldItem.isSelected != newItem.isSelected) return SavedChanged(isSaved = newItem.isSelected)
        return super.getChangePayload(oldItem, newItem)
    }
}