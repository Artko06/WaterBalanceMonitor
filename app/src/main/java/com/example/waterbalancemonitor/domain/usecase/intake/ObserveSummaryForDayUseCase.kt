package com.example.waterbalancemonitor.domain.usecase.intake

import com.example.waterbalancemonitor.domain.model.DailySummary
import com.example.waterbalancemonitor.domain.repository.DailySummaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class ObserveSummaryForDayUseCase @Inject constructor(
    private val dailySummaryRepository: DailySummaryRepository
) {
    operator fun invoke(date: LocalDate): Flow<DailySummary?> =
        dailySummaryRepository.observeByDate(date)
}
