package com.example.waterbalancemonitor.domain.usecase.goal

import com.example.waterbalancemonitor.domain.model.ActivityLevel
import com.example.waterbalancemonitor.domain.model.DailyGoal
import com.example.waterbalancemonitor.domain.model.DailySummary
import com.example.waterbalancemonitor.domain.model.Gender
import com.example.waterbalancemonitor.domain.model.Profile
import com.example.waterbalancemonitor.fake.FakeDailyGoalRepository
import com.example.waterbalancemonitor.fake.FakeDailySummaryRepository
import com.example.waterbalancemonitor.fake.FakeProfileRepository
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class SetGoalBonusUseCaseTest {

    private val date = LocalDate(2026, 10, 15)

    private val goalRepository = FakeDailyGoalRepository()
    private val summaryRepository = FakeDailySummaryRepository()
    private val profileRepository = FakeProfileRepository()
    private val useCase = SetGoalBonusUseCase(
        dailyGoalRepository = goalRepository,
        dailySummaryRepository = summaryRepository,
        profileRepository = profileRepository,
        ensureDailyGoal = EnsureDailyGoalUseCase(goalRepository, summaryRepository)
    )

    @Test
    fun `updates target from base norm and bonus and marks completed`() = runBlocking {
        goalRepository.save(
            DailyGoal(0, date, targetMl = 2000, baseNormMl = 2000, bonusMl = 0, isCompleted = false)
        )
        summaryRepository.put(
            DailySummary(0, date, totalMl = 2500, eventsCount = 3, lastEventAt = null)
        )

        useCase(date, bonusMl = 500)

        val goal = goalRepository.getByDate(date)!!
        assertEquals(500, goal.bonusMl)
        assertEquals(2500, goal.targetMl)
        assertTrue(goal.isCompleted)
    }

    @Test
    fun `keeps day incomplete when target is not reached`() = runBlocking {
        goalRepository.save(
            DailyGoal(0, date, targetMl = 2000, baseNormMl = 2000, bonusMl = 0, isCompleted = true)
        )
        summaryRepository.put(
            DailySummary(0, date, totalMl = 1000, eventsCount = 1, lastEventAt = null)
        )

        useCase(date, bonusMl = 500)

        val goal = goalRepository.getByDate(date)!!
        assertEquals(2500, goal.targetMl)
        assertFalse(goal.isCompleted)
    }

    @Test
    fun `creates goal from profile norm when missing`() = runBlocking {
        profileRepository.profile = profile(dailyNormMl = 2200)
        summaryRepository.put(
            DailySummary(0, date, totalMl = 2100, eventsCount = 2, lastEventAt = null)
        )

        useCase(date, bonusMl = 300)

        val goal = goalRepository.getByDate(date)!!
        assertEquals(2200, goal.baseNormMl)
        assertEquals(300, goal.bonusMl)
        assertEquals(2500, goal.targetMl)
        assertFalse(goal.isCompleted)
    }

    @Test
    fun `clamps bonus to maximum`() = runBlocking {
        goalRepository.save(
            DailyGoal(0, date, targetMl = 2000, baseNormMl = 2000, bonusMl = 0, isCompleted = false)
        )

        useCase(date, bonusMl = 99_999)

        val goal = goalRepository.getByDate(date)!!
        assertEquals(2000, goal.bonusMl)
        assertEquals(4000, goal.targetMl)
    }

    @Test
    fun `does nothing when there is no goal and no profile`() = runBlocking {
        useCase(date, bonusMl = 200)

        assertNull(goalRepository.getByDate(date))
    }

    private fun profile(dailyNormMl: Int) = Profile(
        id = 1,
        name = "Alex",
        weightKg = 70.0,
        heightCm = null,
        age = null,
        gender = Gender.MALE,
        activityLevel = ActivityLevel.MODERATE,
        dailyNormMl = dailyNormMl,
        updatedAt = LocalDateTime(2026, 10, 15, 8, 0)
    )
}
