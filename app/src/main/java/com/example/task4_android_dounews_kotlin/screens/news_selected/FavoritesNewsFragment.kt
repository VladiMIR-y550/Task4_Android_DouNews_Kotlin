package com.example.task4_android_dounews_kotlin.screens.news_selected

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.task4_android_dounews_kotlin.R
import com.example.task4_android_dounews_kotlin.base.BaseFragment
import com.example.task4_android_dounews_kotlin.databinding.FragmentSelectedNewsBinding
import com.example.task4_android_dounews_kotlin.model.entities.ArticleUi
import com.example.task4_android_dounews_kotlin.screens.news_list.NewsListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesNewsFragment : BaseFragment<FragmentSelectedNewsBinding>() {
    private val viewModel: FavoritesViewModel by viewModels()
    private val favoriteAdapter by lazy {
        NewsListAdapter(
            onNewsDetails = {
                findNavController().navigate(
                    FavoritesNewsFragmentDirections.actionFavoritesNewsFragmentToDetailNewsFragment(
                        it.url
                    )
                )
            },
            onNewsFavoriteListener = {
                viewModel.updateIsFavorites(it)
            }
        )
    }

    override val viewBindingProvider: (LayoutInflater, ViewGroup?) -> FragmentSelectedNewsBinding =
        { inflater, container ->
            DataBindingUtil.inflate(inflater, R.layout.fragment_selected_news, container, false)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        checkNetworkStatus()

        binding.rvSelectedNews.apply {
            adapter = favoriteAdapter
            this.setHasFixedSize(true)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.favoriteNewsState.collect(::renderState)
        }
    }

    private fun renderState(favoriteArticles: List<ArticleUi>) {
        favoriteAdapter.submitList(favoriteArticles)
    }

//    private fun checkNetworkStatus() {
//        lifecycleScope.launchWhenStarted {
//            viewModel.changedNetworkStatus
//                .onEach {
//                    if (it == NetworkStatus.Available) {
//                        viewModel.subscribeOnNews()
//                    }
//                }
//                .collect()
//        }
//    }
}