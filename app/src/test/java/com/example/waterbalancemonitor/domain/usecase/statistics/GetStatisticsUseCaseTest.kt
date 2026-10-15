package com.example.waterbalancemonitor.domain.usecase.statistics

import com.example.waterbalancemonitor.domain.model.DailyGoal
import com.example.waterbalancemonitor.domain.model.DailySummary
import com.example.waterbalancemonitor.fake.FakeDailyGoalRepository
import com.example.waterbalancemonitor.fake.FakeDailySummaryRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Test

class GetStatisticsUseCaseTest {

    private val day1 = LocalDate(2026, 10, 10)
    private val day2 = LocalDate(2026, 10, 11)
    private val day3 = LocalDate(2026, 10, 12)

    private val summaryRepository = FakeDailySummaryRepository()
    private val goalRepository = FakeDailyGoalRepository()
    private val useCase = GetStatisticsUseCase(summaryRepository, goalRepository)

    @Test
    fun `aggregates totals, average, completion and extremes`() = runBlocking {
        summaryRepository.put(DailySummary(0, day1, 2000, 3, null))
        summaryRepository.put(DailySummary(0, day2, 1000, 2, null))
        summaryRepository.put(DailySummary(0, day3, 500, 1, null))
        goalRepository.save(DailyGoal(0, day1, 2000, 2000, 0, isCompleted = true))
        goalRepository.save(DailyGoal(0, day2, 2000, 2000, 0, isCompleted = true))
        goalRepository.save(DailyGoal(0, day3, 2000, 2000, 0, isCompleted = false))

        val stats = useCase(day1, day3).first()

        assertEquals(3500, stats.totalMl)
        assertEquals(1166, stats.averageMl)
        assertEquals(3, stats.daysCount)
        assertEquals(2, stats.completedDays)
        assertEquals(66, stats.completedPercent)
        assertEquals(2000, stats.bestDayMl)
        assertEquals(day1, stats.bestDate)
        assertEquals(500, stats.worstDayMl)
        assertEquals(day3, stats.worstDate)
    }

    @Test
    fun `returns zeros when there is no data`() = runBlocking {
        val stats = useCase(day1, day3).first()

        assertEquals(0, stats.totalMl)
        assertEquals(0, stats.averageMl)
        assertEquals(0, stats.daysCount)
        assertEquals(0, stats.completedPercent)
        assertEquals(0, stats.bestDayMl)
        assertEquals(null, stats.bestDate)
        assertEquals(null, stats.worstDate)
    }
}
