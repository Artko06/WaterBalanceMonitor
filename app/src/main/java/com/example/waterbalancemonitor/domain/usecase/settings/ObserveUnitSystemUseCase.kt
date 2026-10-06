package com.example.waterbalancemonitor.domain.usecase.settings

import com.example.waterbalancemonitor.domain.model.UnitSystem
import com.example.waterbalancemonitor.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveUnitSystemUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(): Flow<UnitSystem> = settingsRepository.unitSystem
}
