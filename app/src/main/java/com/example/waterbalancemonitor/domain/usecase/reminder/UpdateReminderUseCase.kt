package com.example.waterbalancemonitor.domain.usecase.reminder

import com.example.waterbalancemonitor.domain.model.Reminder
import com.example.waterbalancemonitor.domain.repository.ReminderRepository
import javax.inject.Inject

class UpdateReminderUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository
) {
    suspend operator fun invoke(reminder: Reminder) = reminderRepository.save(reminder)
}
