package com.example.waterbalancemonitor.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterbalancemonitor.domain.model.ThemeMode
import com.example.waterbalancemonitor.domain.repository.SettingsPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    settingsPreferencesRepository: SettingsPreferencesRepository
) : ViewModel() {

    val themeMode: StateFlow<ThemeMode> = settingsPreferencesRepository.themeMode
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ThemeMode.SYSTEM
        )
}
