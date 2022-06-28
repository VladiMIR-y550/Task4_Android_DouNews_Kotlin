package com.example.task4_android_dounews_kotlin.screens.news_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task4_android_dounews_kotlin.R
import com.example.task4_android_dounews_kotlin.databinding.FragmentNewsListBinding
import com.example.task4_android_dounews_kotlin.model.local.room.ArticleDbEntity
import com.example.task4_android_dounews_kotlin.utils.*
import com.example.task4_android_dounews_kotlin.utils.network.NetworkStatus
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

class NewsListFragment : Fragment() {
    private var _binding: FragmentNewsListBinding? = null
    private val mBinding get() = _binding!!
    private val viewModel: NewsListViewModel by viewModels { factory() }
    private lateinit var newsObserver: Observer<Result<List<ArticleDbEntity>>>
    private lateinit var pagingAdapter: PagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pagingAdapter = PagingAdapter(viewModel)
        pagingAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_list, container, false)

        newsObserver = Observer {
            hideAll()
            when (it) {
                is SuccessResult -> {
                    mBinding.recyclerView.visibility = View.VISIBLE
                    mBinding.tvDbEmpty.visibility = View.GONE
                    mBinding.swipeRefreshList.isRefreshing = false
                    pagingAdapter.articlesList = it.data
                }
                is PendingResult -> {
                    mBinding.swipeRefreshList.isRefreshing = true
                }
                is EmptyResult -> {
                    mBinding.recyclerView.visibility = View.GONE
                    mBinding.tvDbEmpty.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), "Result is Empty", Toast.LENGTH_SHORT).show()
                }
                is ErrorResult -> {
                    Toast.makeText(
                        requireContext(),
                        it.error.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        checkNetworkStatus()

        mBinding.swipeRefreshList.setOnRefreshListener {
            viewModel.updateNews()
            mBinding.swipeRefreshList.isRefreshing = false
        }

        mBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = pagingAdapter
            this.setHasFixedSize(true)
        }
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.articles.observe(viewLifecycleOwner, newsObserver)
    }

    override fun onStop() {
        super.onStop()
        viewModel.articles.removeObserver(newsObserver)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun hideAll() {
        mBinding.tvDbEmpty.visibility = View.GONE
        mBinding.pbNewsList.visibility = View.GONE
        mBinding.swipeRefreshList.isRefreshing = false
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