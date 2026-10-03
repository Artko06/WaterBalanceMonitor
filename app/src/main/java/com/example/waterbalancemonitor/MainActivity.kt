package com.example.waterbalancemonitor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.waterbalancemonitor.presentation.MainViewModel
import com.example.waterbalancemonitor.presentation.navigation.WaterBalanceApp
import com.example.waterbalancemonitor.presentation.theme.WaterBalanceMonitorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MainViewModel = hiltViewModel()
            val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()
            WaterBalanceMonitorTheme(themeMode = themeMode) {
                WaterBalanceApp()
            }
        }
    }
}
