package com.example.waterbalancemonitor

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.waterbalancemonitor.domain.model.AppLanguage
import com.example.waterbalancemonitor.presentation.MainViewModel
import com.example.waterbalancemonitor.presentation.navigation.WaterBalanceApp
import com.example.waterbalancemonitor.presentation.theme.WaterBalanceMonitorTheme
import com.example.waterbalancemonitor.presentation.theme.isDark
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()

            val isDarkTheme = state.themeMode.isDark()

            DisposableEffect(isDarkTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        lightScrim = Color.TRANSPARENT,
                        darkScrim = Color.TRANSPARENT,
                        detectDarkMode = { isDarkTheme }
                    ),
                    navigationBarStyle = SystemBarStyle.auto(
                        lightScrim = Color.TRANSPARENT,
                        darkScrim = Color.TRANSPARENT,
                        detectDarkMode = { isDarkTheme }
                    )
                )
                onDispose { }
            }

            val baseContext = LocalContext.current
            val localizedContext = remember(state.language, baseContext) {
                baseContext.withLocale(state.language)
            }

            CompositionLocalProvider(
                LocalContext provides localizedContext,
                LocalConfiguration provides localizedContext.resources.configuration,
                LocalResources provides localizedContext.resources
            ) {
                WaterBalanceMonitorTheme(themeMode = state.themeMode) {
                    WaterBalanceApp(startDestination = state.startRoute)
                }
            }
        }
    }
}

private fun Context.withLocale(language: AppLanguage): Context {
    val tag = language.tag ?: return this
    val locale = Locale.forLanguageTag(tag)
    Locale.setDefault(locale)
    val configuration = Configuration(resources.configuration).apply { setLocale(locale) }
    val localizedResources = createConfigurationContext(configuration).resources
    return LocalizedContext(this, localizedResources)
}

private class LocalizedContext(
    base: Context,
    private val localizedResources: Resources
) : ContextWrapper(base) {
    override fun getResources(): Resources = localizedResources
}
