package com.example.waterbalancemonitor.domain.usecase.settings

import com.example.waterbalancemonitor.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.DayOfWeek
import javax.inject.Inject

class ObserveFirstDayOfWeekUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(): Flow<DayOfWeek> = settingsRepository.firstDayOfWeek
}
