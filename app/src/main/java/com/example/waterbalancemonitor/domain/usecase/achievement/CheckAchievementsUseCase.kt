package com.example.waterbalancemonitor.domain.usecase.achievement

import com.example.waterbalancemonitor.domain.model.Achievement
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

        val existing = userAchievementRepository.observeAll().first()
            .associateBy { it.achievementId }
        val events = intakeEventRepository.observeAll().first()
        val summaries = dailySummaryRepository.observeBetween(EPOCH, today).first()
        val goals = dailyGoalRepository.observeBetween(EPOCH, today).first()
        val totalMl = summaries.sumOf { it.totalMl }
        val completedDates = goals.filter { it.isCompleted }.map { it.date }.toSet()
        val streak = currentStreak(completedDates, today)

        val completedCodes = achievements
            .filter { existing[it.id]?.isCompleted == true }
            .map { it.code }
            .toMutableSet()

        orderedByDepth(achievements).forEach { achievement ->
            val previous = existing[achievement.id]
            val available = achievement.prerequisiteCode == null ||
                achievement.prerequisiteCode in completedCodes

            if (!available && previous?.isCompleted != true) {
                userAchievementRepository.save(
                    UserAchievement(
                        id = previous?.id ?: 0,
                        achievementId = achievement.id,
                        progress = 0,
                        isCompleted = false,
                        completedAt = previous?.completedAt
                    )
                )
                return@forEach
            }

            val progress = progressFor(
                conditionType = achievement.conditionType,
                conditionValue = achievement.conditionValue,
                events = events,
                completedDays = completedDates.size,
                totalMl = totalMl,
                streak = streak
            )
            val reached = achievement.conditionValue in 1..progress
            val isCompleted = reached || previous?.isCompleted == true
            val completedAt = previous?.completedAt ?: if (isCompleted) now() else null

            userAchievementRepository.save(
                UserAchievement(
                    id = previous?.id ?: 0,
                    achievementId = achievement.id,
                    progress = progress,
                    isCompleted = isCompleted,
                    completedAt = completedAt
                )
            )
            if (isCompleted) completedCodes.add(achievement.code)
        }
    }

    private fun orderedByDepth(achievements: List<Achievement>): List<Achievement> {
        val byCode = achievements.associateBy { it.code }
        return achievements.sortedBy { depth(it, byCode) }
    }

    private fun depth(achievement: Achievement, byCode: Map<String, Achievement>): Int {
        var depth = 0
        var code = achievement.prerequisiteCode
        val visited = mutableSetOf<String>()
        while (code != null && visited.add(code)) {
            depth++
            code = byCode[code]?.prerequisiteCode
        }
        return depth
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
