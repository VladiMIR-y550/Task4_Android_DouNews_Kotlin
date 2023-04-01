package com.example.task4_android_dounews_kotlin

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.task4_android_dounews_kotlin.databinding.ActivityMainBinding
import com.example.task4_android_dounews_kotlin.screens.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private var _binding: ActivityMainBinding? = null
    private val mBinding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(mBinding.toolBar)

        navController = Navigation.findNavController(this, R.id.nav_host)
        val appBarConfig = AppBarConfiguration(navController.graph)
        mBinding.toolBar.setupWithNavController(navController, appBarConfig)

        subscribeOnNetworkStatus()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_selected_news, menu)
        return true
    }

    private fun subscribeOnNetworkStatus() {
        mBinding.viewModel = viewModel
        lifecycleScope.launchWhenStarted {
            viewModel.changedNetworkStatus
                .onEach { mBinding.viewModel = viewModel }
                .collect()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}