package com.example.waterbalancemonitor.domain.usecase.achievement

import com.example.waterbalancemonitor.domain.model.AchievementConditionType
import com.example.waterbalancemonitor.domain.model.IntakeEvent
import com.example.waterbalancemonitor.domain.model.UserAchievement
import com.example.waterbalancemonitor.domain.repository.AchievementRepository
import com.example.waterbalancemonitor.domain.repository.DailyGoalRepository
import com.example.waterbalancemonitor.domain.repository.DailySummaryRepository
import com.example.waterbalancemonitor.domain.repository.IntakeEventRepository
import com.example.waterbalancemonitor.domain.repository.UserAchievementRepository
import kotlinx.coroutines.flow.first
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import javax.inject.Inject
import kotlin.time.Clock

class CheckAchievementsUseCase @Inject constructor(
    private val achievementRepository: AchievementRepository,
    private val userAchievementRepository: UserAchievementRepository,
    private val intakeEventRepository: IntakeEventRepository,
    private val dailySummaryRepository: DailySummaryRepository,
    private val dailyGoalRepository: DailyGoalRepository
) {

    suspend operator fun invoke() {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val achievements = achievementRepository.getAll()
        if (achievements.isEmpty()) return

        val events = intakeEventRepository.observeAll().first()
        val summaries = dailySummaryRepository.observeBetween(EPOCH, today).first()
        val goals = dailyGoalRepository.observeBetween(EPOCH, today).first()
        val totalMl = summaries.sumOf { it.totalMl }
        val completedDates = goals.filter { it.isCompleted }.map { it.date }.toSet()
        val streak = currentStreak(completedDates, today)

        achievements.forEach { achievement ->
            val progress = progressFor(achievement.conditionType, achievement.conditionValue, events, completedDates.size, totalMl, streak)
            val isUnlocked = achievement.conditionValue in 1..progress
            val existing = userAchievementRepository.getByAchievementId(achievement.id)
            val unlocked = isUnlocked || existing?.isUnlocked == true
            userAchievementRepository.save(
                UserAchievement(
                    id = existing?.id ?: 0,
                    achievementId = achievement.id,
                    progress = progress,
                    isUnlocked = unlocked,
                    unlockedAt = existing?.unlockedAt ?: if (isUnlocked) now() else null
                )
            )
        }
    }

    private fun progressFor(
        conditionType: AchievementConditionType,
        conditionValue: Int,
        events: List<IntakeEvent>,
        completedDays: Int,
        totalMl: Int,
        streak: Int
    ): Int = when (conditionType) {
        AchievementConditionType.FIRST_INTAKE -> events.size.coerceAtMost(conditionValue)
        AchievementConditionType.DAILY_NORM_REACHED -> completedDays
        AchievementConditionType.STREAK_DAYS -> streak
        AchievementConditionType.TOTAL_VOLUME_ML -> totalMl
        AchievementConditionType.INTAKE_BEFORE_HOUR ->
            if (events.any { it.consumedAt.hour < conditionValue }) conditionValue else 0
        AchievementConditionType.INTAKE_AFTER_HOUR ->
            if (events.any { it.consumedAt.hour >= conditionValue }) conditionValue else 0
        AchievementConditionType.UNKNOWN -> 0
    }

    private fun currentStreak(completedDates: Set<LocalDate>, today: LocalDate): Int {
        var streak = 0
        var day = today
        while (day in completedDates) {
            streak++
            day -= DatePeriod(days = 1)
        }
        return streak
    }

    private fun now() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    private companion object {
        val EPOCH = LocalDate(1970, 1, 1)
    }
}
