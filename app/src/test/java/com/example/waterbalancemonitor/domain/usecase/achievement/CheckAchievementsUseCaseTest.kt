package com.example.waterbalancemonitor.domain.usecase.achievement

import com.example.waterbalancemonitor.domain.model.Achievement
import com.example.waterbalancemonitor.domain.model.AchievementCategory
import com.example.waterbalancemonitor.domain.model.AchievementConditionType
import com.example.waterbalancemonitor.domain.model.AchievementIcon
import com.example.waterbalancemonitor.domain.model.DailyGoal
import com.example.waterbalancemonitor.domain.model.DailySummary
import com.example.waterbalancemonitor.domain.model.IntakeEvent
import com.example.waterbalancemonitor.domain.model.UserAchievement
import com.example.waterbalancemonitor.fake.FakeAchievementRepository
import com.example.waterbalancemonitor.fake.FakeDailyGoalRepository
import com.example.waterbalancemonitor.fake.FakeDailySummaryRepository
import com.example.waterbalancemonitor.fake.FakeIntakeEventRepository
import com.example.waterbalancemonitor.fake.FakeUserAchievementRepository
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.todayIn
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.time.Clock

class CheckAchievementsUseCaseTest {

    private val today: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())

    private val achievementRepository = FakeAchievementRepository()
    private val userAchievementRepository = FakeUserAchievementRepository()
    private val intakeRepository = FakeIntakeEventRepository()
    private val summaryRepository = FakeDailySummaryRepository()
    private val goalRepository = FakeDailyGoalRepository()
    private val useCase = CheckAchievementsUseCase(
        achievementRepository = achievementRepository,
        userAchievementRepository = userAchievementRepository,
        intakeEventRepository = intakeRepository,
        dailySummaryRepository = summaryRepository,
        dailyGoalRepository = goalRepository
    )

    @Test
    fun `completes first intake achievement`() = runBlocking {
        achievementRepository.add(achievement(1, AchievementConditionType.FIRST_INTAKE, 1))
        intakeRepository.add(IntakeEvent(1, 1, null, 250, LocalDateTime(today, LocalTime(9, 0))))

        useCase()

        val user = userAchievementRepository.getByAchievementId(1)!!
        assertTrue(user.isCompleted)
        assertEquals(1, user.progress)
    }

    @Test
    fun `completes daily norm achievement when a goal is completed`() = runBlocking {
        achievementRepository.add(achievement(1, AchievementConditionType.DAILY_NORM_REACHED, 1))
        goalRepository.save(DailyGoal(0, today, 2000, 2000, 0, isCompleted = true))

        useCase()

        assertTrue(userAchievementRepository.getByAchievementId(1)!!.isCompleted)
    }

    @Test
    fun `tracks streak of consecutive completed days`() = runBlocking {
        achievementRepository.add(achievement(1, AchievementConditionType.STREAK_DAYS, 3))
        goalRepository.save(DailyGoal(0, today, 2000, 2000, 0, true))
        goalRepository.save(DailyGoal(0, today.minus(DatePeriod(days = 1)), 2000, 2000, 0, true))
        goalRepository.save(DailyGoal(0, today.minus(DatePeriod(days = 2)), 2000, 2000, 0, true))

        useCase()

        val user = userAchievementRepository.getByAchievementId(1)!!
        assertTrue(user.isCompleted)
        assertEquals(3, user.progress)
    }

    @Test
    fun `accumulates total volume without completing`() = runBlocking {
        achievementRepository.add(achievement(1, AchievementConditionType.TOTAL_VOLUME_ML, 5000))
        summaryRepository.put(DailySummary(0, today, 2000, 3, null))

        useCase()

        val user = userAchievementRepository.getByAchievementId(1)!!
        assertEquals(2000, user.progress)
        assertFalse(user.isCompleted)
    }

    @Test
    fun `keeps achievement completed once reached`() = runBlocking {
        achievementRepository.add(achievement(1, AchievementConditionType.FIRST_INTAKE, 1))
        userAchievementRepository.save(
            UserAchievement(
                id = 1,
                achievementId = 1,
                progress = 1,
                isCompleted = true,
                completedAt = null
            )
        )

        useCase()

        assertTrue(userAchievementRepository.getByAchievementId(1)!!.isCompleted)
    }

    @Test
    fun `locks achievement until prerequisite is completed`() = runBlocking {
        achievementRepository.add(achievement(1, AchievementConditionType.FIRST_INTAKE, 1, code = "A"))
        achievementRepository.add(
            achievement(
                2,
                AchievementConditionType.TOTAL_VOLUME_ML,
                5000,
                code = "B",
                prerequisiteCode = "A"
            )
        )
        summaryRepository.put(DailySummary(0, today, 5000, 3, null))

        useCase()

        val user = userAchievementRepository.getByAchievementId(2)!!
        assertFalse(user.isCompleted)
        assertEquals(0, user.progress)
    }

    @Test
    fun `unlocks prerequisite chain`() = runBlocking {
        achievementRepository.add(achievement(1, AchievementConditionType.FIRST_INTAKE, 1, code = "A"))
        achievementRepository.add(
            achievement(
                2,
                AchievementConditionType.TOTAL_VOLUME_ML,
                5000,
                code = "B",
                prerequisiteCode = "A"
            )
        )
        intakeRepository.add(IntakeEvent(1, 1, null, 250, LocalDateTime(today, LocalTime(9, 0))))
        summaryRepository.put(DailySummary(0, today, 5000, 3, null))

        useCase()

        assertTrue(userAchievementRepository.getByAchievementId(1)!!.isCompleted)
        assertTrue(userAchievementRepository.getByAchievementId(2)!!.isCompleted)
    }

    private fun achievement(
        id: Long,
        type: AchievementConditionType,
        value: Int,
        code: String = "CODE_$id",
        prerequisiteCode: String? = null
    ) = Achievement(
        id = id,
        code = code,
        title = "Title $id",
        description = "Description $id",
        category = AchievementCategory.OTHER,
        conditionType = type,
        conditionValue = value,
        icon = AchievementIcon.DEFAULT,
        prerequisiteCode = prerequisiteCode
    )
}
