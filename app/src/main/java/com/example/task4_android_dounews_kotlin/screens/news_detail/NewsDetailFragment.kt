package com.example.task4_android_dounews_kotlin.screens.news_detail

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.task4_android_dounews_kotlin.databinding.FragmentDetailNewsBinding
import com.example.task4_android_dounews_kotlin.utils.network.NetworkStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class NewsDetailFragment : Fragment() {

    private val args by navArgs<NewsDetailFragmentArgs>()
    private var bindingInternal: FragmentDetailNewsBinding? = null
    private val binding get() = bindingInternal!!
    private val viewModel: NewsDetailViewModel by viewModels()
    private lateinit var scrollObserver: Observer<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.downloadDetailedNews(args.currentUrl)
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingInternal = FragmentDetailNewsBinding.inflate(inflater, container, false)

        scrollObserver = Observer {
            binding.webViewDetailed.scrollTo(0, it)
        }

        binding.viewModel = viewModel

        webViewSetup()
        checkNetworkStatus()
        return binding.root
    }

    override fun onStart() {
        viewModel.scrollPosition.observe(viewLifecycleOwner, scrollObserver)
        super.onStart()
    }

    override fun onStop() {
        viewModel.scrollPosition.removeObserver(scrollObserver)
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.saveScrollPosition(binding.webViewDetailed.scrollY)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingInternal = null
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewSetup() {
        binding.viewModel = viewModel
        binding.webViewDetailed.apply {
            settings.javaScriptEnabled = true
            settings.builtInZoomControls = true
            settings.displayZoomControls = false
        }

        binding.webViewDetailed.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.webViewDetailed.visibility = View.GONE
                binding.pbDetail.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.pbDetail.visibility = View.GONE
                binding.webViewDetailed.visibility = View.VISIBLE
            }
        }
    }

    private fun checkNetworkStatus() {
        lifecycleScope.launchWhenStarted {
            viewModel.changedNetworkStatus
                .onEach {
                    if (it == NetworkStatus.Available) {
                        binding.viewModel = viewModel
                    }
                }
                .collect()
        }
    }
}