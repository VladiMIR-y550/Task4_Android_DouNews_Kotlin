package com.example.task4_android_dounews_kotlin.screens.news_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task4_android_dounews_kotlin.R
import com.example.task4_android_dounews_kotlin.databinding.ItemArticleBinding
import com.example.task4_android_dounews_kotlin.domain.modelsUi.ArticleUi
import com.example.task4_android_dounews_kotlin.utils.PRELOAD_ARTICLES

class NewsListAdapter(
    private val loadPage: (page: Int) -> Unit
) : ListAdapter<ArticleUi, ArticleViewHolder>(ArticleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ArticleViewHolder(ItemArticleBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = currentList[position]
        holder.bind(article)

        if (position == currentList.size - PRELOAD_ARTICLES) {
            loadPage(currentList.size)
        }

        holder.itemView.setOnClickListener {
            holder.itemView.findNavController().navigate(
                NewsListFragmentDirections.actionNewsListFragmentToDetailNewsFragment(article.url)
            )
        }
    }
}

class ArticleViewHolder(
    private val binding: ItemArticleBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(article: ArticleUi) {
        binding.article = article

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
}

class ArticleDiffCallback : DiffUtil.ItemCallback<ArticleUi>() {
    override fun areItemsTheSame(oldItem: ArticleUi, newItem: ArticleUi): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ArticleUi, newItem: ArticleUi): Boolean {
        return oldItem == newItem
    }
}