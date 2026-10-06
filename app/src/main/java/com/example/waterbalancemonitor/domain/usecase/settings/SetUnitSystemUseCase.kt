package com.example.waterbalancemonitor.domain.usecase.settings

import com.example.waterbalancemonitor.domain.model.UnitSystem
import com.example.waterbalancemonitor.domain.repository.SettingsRepository
import javax.inject.Inject

class SetUnitSystemUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(unitSystem: UnitSystem) =
        settingsRepository.setUnitSystem(unitSystem)
}
