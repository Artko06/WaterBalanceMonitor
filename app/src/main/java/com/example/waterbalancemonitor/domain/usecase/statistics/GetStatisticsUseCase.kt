package com.example.waterbalancemonitor.domain.usecase.statistics

import com.example.waterbalancemonitor.domain.model.Statistics
import com.example.waterbalancemonitor.domain.repository.DailyGoalRepository
import com.example.waterbalancemonitor.domain.repository.DailySummaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class GetStatisticsUseCase @Inject constructor(
    private val dailySummaryRepository: DailySummaryRepository,
    private val dailyGoalRepository: DailyGoalRepository
) {
    operator fun invoke(from: LocalDate, to: LocalDate): Flow<Statistics> = combine(
        dailySummaryRepository.observeBetween(from, to),
        dailyGoalRepository.observeBetween(from, to)
    ) { summaries, goals ->
        val totalMl = summaries.sumOf { it.totalMl }
        val daysCount = summaries.size
        val bestDay = summaries.maxByOrNull { it.totalMl }
        Statistics(
            from = from,
            to = to,
            totalMl = totalMl,
            averageMl = if (daysCount > 0) totalMl / daysCount else 0,
            daysCount = daysCount,
            completedDays = goals.count { it.isCompleted },
            bestDayMl = bestDay?.totalMl ?: 0,
            bestDate = bestDay?.date
        )
    }
}
