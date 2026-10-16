package com.example.waterbalancemonitor.presentation.screens.achievements

import com.example.waterbalancemonitor.domain.model.Achievement
import com.example.waterbalancemonitor.domain.model.AchievementCategory
import com.example.waterbalancemonitor.domain.model.AchievementConditionType
import com.example.waterbalancemonitor.domain.model.AchievementIcon
import com.example.waterbalancemonitor.domain.model.AchievementStatus
import com.example.waterbalancemonitor.domain.model.UserAchievement
import org.junit.Assert.assertEquals
import org.junit.Test

class AchievementItemMapperTest {

    private val definitions = listOf(
        achievement(1, "A", AchievementCategory.START, prerequisiteCode = null),
        achievement(2, "B", AchievementCategory.STREAK, prerequisiteCode = "A"),
        achievement(3, "C", AchievementCategory.TOTAL, prerequisiteCode = "B")
    )

    @Test
    fun `locks achievements whose prerequisite is not completed`() {
        val items = buildAchievementItems(definitions, emptyList())

        assertEquals(AchievementStatus.IN_PROGRESS, items.first { it.id == 1L }.status)
        assertEquals(AchievementStatus.LOCKED, items.first { it.id == 2L }.status)
        assertEquals(AchievementStatus.LOCKED, items.first { it.id == 3L }.status)
    }

    @Test
    fun `unlocks next step when prerequisite is completed`() {
        val userAchievements = listOf(userAchievement(achievementId = 1, isCompleted = true))

        val items = buildAchievementItems(definitions, userAchievements)

        assertEquals(AchievementStatus.COMPLETED, items.first { it.id == 1L }.status)
        assertEquals(AchievementStatus.IN_PROGRESS, items.first { it.id == 2L }.status)
        assertEquals(AchievementStatus.LOCKED, items.first { it.id == 3L }.status)
    }

    @Test
    fun `groups by category in declaration order and skips empty`() {
        val items = buildAchievementItems(definitions, emptyList())

        val groups = groupAchievements(items)

        assertEquals(
            listOf(AchievementCategory.START, AchievementCategory.STREAK, AchievementCategory.TOTAL),
            groups.map { it.category }
        )
        assertEquals(1, groups.first { it.category == AchievementCategory.START }.totalCount)
    }

    private fun achievement(
        id: Long,
        code: String,
        category: AchievementCategory,
        prerequisiteCode: String?
    ) = Achievement(
        id = id,
        code = code,
        title = "Title $id",
        description = "Description $id",
        category = category,
        conditionType = AchievementConditionType.FIRST_INTAKE,
        conditionValue = 1,
        icon = AchievementIcon.DEFAULT,
        prerequisiteCode = prerequisiteCode
    )

    private fun userAchievement(achievementId: Long, isCompleted: Boolean) = UserAchievement(
        id = achievementId,
        achievementId = achievementId,
        progress = if (isCompleted) 1 else 0,
        isCompleted = isCompleted,
        completedAt = null
    )
}
