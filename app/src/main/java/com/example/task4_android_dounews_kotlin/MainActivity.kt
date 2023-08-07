package com.example.task4_android_dounews_kotlin

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.task4_android_dounews_kotlin.databinding.ActivityMainBinding
import com.example.task4_android_dounews_kotlin.screens.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private var bindingInternal: ActivityMainBinding? = null
    private val binding get() = bindingInternal!!
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingInternal = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(binding.toolBar)

        navController = Navigation.findNavController(this, R.id.nav_host)
        val appBarConfig = AppBarConfiguration(navController.graph)
        binding.toolBar.setupWithNavController(navController, appBarConfig)

        subscribeOnNetworkStatus()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_selected_news, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun subscribeOnNetworkStatus() {
        binding.viewModel = viewModel
        lifecycleScope.launchWhenStarted {
            viewModel.changedNetworkStatus
                .onEach { binding.viewModel = viewModel }
                .collect()
        }
    }

    override fun onDestroy() {
        bindingInternal = null
        super.onDestroy()
    }
}