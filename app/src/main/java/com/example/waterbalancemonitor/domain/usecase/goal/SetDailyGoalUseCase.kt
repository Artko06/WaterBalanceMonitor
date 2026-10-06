package com.example.waterbalancemonitor.domain.usecase.goal

import com.example.waterbalancemonitor.domain.model.DailyGoal
import com.example.waterbalancemonitor.domain.repository.DailyGoalRepository
import javax.inject.Inject

class SetDailyGoalUseCase @Inject constructor(
    private val dailyGoalRepository: DailyGoalRepository
) {
    suspend operator fun invoke(goal: DailyGoal) = dailyGoalRepository.save(goal)
}
