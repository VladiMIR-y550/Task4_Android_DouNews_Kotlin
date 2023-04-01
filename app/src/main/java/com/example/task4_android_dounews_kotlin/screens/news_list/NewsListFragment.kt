package com.example.task4_android_dounews_kotlin.screens.news_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.task4_android_dounews_kotlin.R
import com.example.task4_android_dounews_kotlin.base.BaseFragment
import com.example.task4_android_dounews_kotlin.databinding.FragmentNewsListBinding
import com.example.task4_android_dounews_kotlin.domain.modelsUi.ArticleUi
import com.example.task4_android_dounews_kotlin.utils.*
import com.example.task4_android_dounews_kotlin.utils.network.NetworkStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class NewsListFragment : BaseFragment<FragmentNewsListBinding>() {
    private val viewModel: NewsListViewModel by viewModels()
    private val pagingAdapter by lazy {
        NewsListAdapter(loadPage = {
            viewModel.loadPage(it)
        })
    }

    override val viewBindingProvider: (LayoutInflater, ViewGroup?) -> FragmentNewsListBinding =
        { inflater, container ->
            DataBindingUtil.inflate(inflater, R.layout.fragment_news_list, container, false)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pagingAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.articles.observe(viewLifecycleOwner, ::renderState)
        checkNetworkStatus()

        binding.swipeRefreshList.setOnRefreshListener {
            viewModel.updateNews()
            binding.swipeRefreshList.isRefreshing = false
        }

        binding.recyclerView.apply {
            adapter = pagingAdapter
            this.setHasFixedSize(true)
        }
    }

    private fun renderState(state: Result<List<ArticleUi>>) {
        hideAll()
        when (state) {
            is SuccessResult -> {
                binding.recyclerView.visibility = View.VISIBLE
//                binding.tvDbEmpty.visibility = View.GONE
                binding.swipeRefreshList.isRefreshing = false
                pagingAdapter.submitList(state.data)
            }
            is PendingResult -> {
                binding.swipeRefreshList.isRefreshing = true
            }
            is EmptyResult -> {
                binding.recyclerView.visibility = View.GONE
//                binding.tvDbEmpty.visibility = View.VISIBLE
                Toast.makeText(requireContext(), "Result is Empty", Toast.LENGTH_SHORT).show()
            }
            is ErrorResult -> {
                Toast.makeText(
                    requireContext(),
                    state.error.localizedMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun hideAll() {
//        binding.tvDbEmpty.visibility = View.GONE
//        binding.pbNewsList.visibility = View.GONE
        binding.swipeRefreshList.isRefreshing = false
    }

    private fun checkNetworkStatus() {
        lifecycleScope.launchWhenStarted {
            viewModel.changedNetworkStatus
                .onEach {
                    if (it == NetworkStatus.Available) {
                        viewModel.subscribeOnNews()
                    }
                }
                .collect()
        }
    }
}