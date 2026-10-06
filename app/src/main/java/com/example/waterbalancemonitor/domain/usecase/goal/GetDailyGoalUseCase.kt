package com.example.waterbalancemonitor.domain.usecase.goal

import com.example.waterbalancemonitor.domain.model.DailyGoal
import com.example.waterbalancemonitor.domain.repository.DailyGoalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class GetDailyGoalUseCase @Inject constructor(
    private val dailyGoalRepository: DailyGoalRepository
) {
    operator fun invoke(date: LocalDate): Flow<DailyGoal?> =
        dailyGoalRepository.observeByDate(date)
}
