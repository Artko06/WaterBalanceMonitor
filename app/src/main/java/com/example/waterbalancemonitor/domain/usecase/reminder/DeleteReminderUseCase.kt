package com.example.waterbalancemonitor.domain.usecase.reminder

import com.example.waterbalancemonitor.domain.repository.ReminderRepository
import javax.inject.Inject

class DeleteReminderUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository
) {
    suspend operator fun invoke(id: Long) = reminderRepository.deleteById(id)
}
