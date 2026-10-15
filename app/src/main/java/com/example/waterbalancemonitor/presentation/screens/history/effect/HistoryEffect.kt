package com.example.waterbalancemonitor.presentation.screens.history.effect

sealed interface HistoryEffect {
    data object ShowSaveError : HistoryEffect
}
