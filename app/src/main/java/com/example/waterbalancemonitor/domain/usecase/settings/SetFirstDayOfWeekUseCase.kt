package com.example.waterbalancemonitor.domain.usecase.settings

import com.example.waterbalancemonitor.domain.repository.SettingsRepository
import kotlinx.datetime.DayOfWeek
import javax.inject.Inject

class SetFirstDayOfWeekUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(dayOfWeek: DayOfWeek) =
        settingsRepository.setFirstDayOfWeek(dayOfWeek)
}
