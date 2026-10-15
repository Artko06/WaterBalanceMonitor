package com.example.waterbalancemonitor.domain.usecase.intake

import com.example.waterbalancemonitor.fake.FakeIntakeEventRepository
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.junit.Assert.assertEquals
import org.junit.Test

class ObserveIntakesForDayUseCaseTest {

    private val repository = FakeIntakeEventRepository()
    private val useCase = ObserveIntakesForDayUseCase(repository)
    private val date = LocalDate(2026, 10, 15)

    @Test
    fun `observes intakes within full day boundaries`() {
        useCase(date)

        assertEquals(LocalDateTime(date, LocalTime(0, 0)), repository.lastFrom)
        assertEquals(
            LocalDateTime(date, LocalTime(23, 59, 59, 999_999_999)),
            repository.lastTo
        )
    }
}
