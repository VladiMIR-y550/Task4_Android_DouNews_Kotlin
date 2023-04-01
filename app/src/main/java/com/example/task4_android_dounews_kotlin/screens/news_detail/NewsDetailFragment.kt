package com.example.task4_android_dounews_kotlin.screens.news_detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.task4_android_dounews_kotlin.R
import com.example.task4_android_dounews_kotlin.base.BaseFragment
import com.example.task4_android_dounews_kotlin.databinding.FragmentDetailNewsBinding
import com.example.task4_android_dounews_kotlin.domain.modelsUi.WebPageUi
import com.example.task4_android_dounews_kotlin.utils.network.NetworkStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class NewsDetailFragment : BaseFragment<FragmentDetailNewsBinding>() {

    private val args by navArgs<NewsDetailFragmentArgs>()
    private val viewModel: NewsDetailViewModel by viewModels()

    override val viewBindingProvider: (LayoutInflater, ViewGroup?) -> FragmentDetailNewsBinding =
        { inflater, container ->
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail_news, container, false)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.downloadDetailedNews(args.currentUrl)
        viewModel.webPageState.observe(viewLifecycleOwner, ::renderState)
        webViewSetup()
        checkNetworkStatus()
    }

    private fun renderState(webPageUiState: WebPageUi) {
        with(binding) {
            Log.d("TAG", "Url ${webPageUiState.selectedNews}")
            webPageUi = webPageUiState
            webViewDetailed.scrollTo(0, webPageUiState.scrollPagePosition)
            pbDetail.isVisible = webPageUiState.isProgress

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.saveScrollPosition(binding.webViewDetailed.scrollY)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewSetup() {
        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().setAcceptThirdPartyCookies(binding.webViewDetailed, true)
        binding.webViewDetailed.apply {
            setInitialScale(1)
            settings.javaScriptEnabled = true
            settings.builtInZoomControls = true
            settings.displayZoomControls = false
            settings.allowContentAccess = true
            settings.allowFileAccess = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.domStorageEnabled = true
        }
        binding.webViewDetailed.webViewClient = object : WebViewClient() {
            override fun onPageCommitVisible(view: WebView?, url: String?) {
                super.onPageCommitVisible(view, url)
                viewModel.updateIsProgress(false)
            }
        }
    }

    private fun checkNetworkStatus() {
        lifecycleScope.launchWhenStarted {
            viewModel.changedNetworkStatus
                .onEach {
                    if (it == NetworkStatus.Available) {
                        binding.webPageUi = viewModel.webPageState.value
                    }
                }
                .collect()
        }
    }
}