package com.example.waterbalancemonitor.domain.usecase.intake

import com.example.waterbalancemonitor.domain.model.DailySummary
import com.example.waterbalancemonitor.domain.repository.DailySummaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import javax.inject.Inject
import kotlin.time.Clock

class GetTodaySummaryUseCase @Inject constructor(
    private val dailySummaryRepository: DailySummaryRepository
) {
    operator fun invoke(): Flow<DailySummary?> {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        return dailySummaryRepository.observeByDate(today)
    }
}
