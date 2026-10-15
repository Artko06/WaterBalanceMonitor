package com.example.waterbalancemonitor.domain.usecase.goal

import com.example.waterbalancemonitor.domain.model.DailyGoal
import com.example.waterbalancemonitor.domain.repository.DailyGoalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class ObserveGoalsForRangeUseCase @Inject constructor(
    private val dailyGoalRepository: DailyGoalRepository
) {
    operator fun invoke(from: LocalDate, to: LocalDate): Flow<List<DailyGoal>> =
        dailyGoalRepository.observeBetween(from, to)
}
