package com.example.waterbalancemonitor.domain.usecase.reminder

import com.example.waterbalancemonitor.domain.model.Reminder
import com.example.waterbalancemonitor.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveRemindersUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository
) {
    operator fun invoke(): Flow<List<Reminder>> = reminderRepository.observeAll()
}
