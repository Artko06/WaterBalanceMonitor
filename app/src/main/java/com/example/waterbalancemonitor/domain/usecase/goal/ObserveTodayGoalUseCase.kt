package com.example.waterbalancemonitor.domain.usecase.goal

import com.example.waterbalancemonitor.domain.model.DailyGoal
import com.example.waterbalancemonitor.domain.repository.DailyGoalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import javax.inject.Inject
import kotlin.time.Clock

class ObserveTodayGoalUseCase @Inject constructor(
    private val dailyGoalRepository: DailyGoalRepository
) {
    operator fun invoke(): Flow<DailyGoal?> {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        return dailyGoalRepository.observeByDate(today)
    }
}
