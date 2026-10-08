package com.example.waterbalancemonitor.presentation.screens.profile.effect

sealed interface ProfileEffect {
    data object NavigateToNormInfo : ProfileEffect
}
