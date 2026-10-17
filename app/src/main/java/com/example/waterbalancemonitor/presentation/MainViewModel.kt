package com.example.waterbalancemonitor.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterbalancemonitor.domain.usecase.profile.ObserveProfileUseCase
import com.example.waterbalancemonitor.domain.usecase.settings.ObserveLanguageUseCase
import com.example.waterbalancemonitor.domain.usecase.settings.ObserveThemeModeUseCase
import com.example.waterbalancemonitor.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val observeThemeModeUseCase: ObserveThemeModeUseCase,
    private val observeLanguageUseCase: ObserveLanguageUseCase,
    private val observeProfile: ObserveProfileUseCase
) : ViewModel() {

    private val startRoute = flow {
        val hasProfile = observeProfile().first() != null
        emit(if (hasProfile) Screen.Dashboard.route else Screen.Onboarding.route)
    }

    val state: StateFlow<MainState> = combine(
        observeThemeModeUseCase(),
        observeLanguageUseCase(),
        startRoute
    ) { themeMode, language, route ->
        MainState(
            themeMode = themeMode,
            language = language,
            startRoute = route
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = MainState()
    )
}
