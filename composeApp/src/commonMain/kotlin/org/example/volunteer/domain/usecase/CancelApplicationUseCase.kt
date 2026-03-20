package org.example.volunteer.domain.usecase

import org.example.volunteer.core.common.Result
import org.example.volunteer.domain.repository.ApplicationRepository
import org.example.volunteer.domain.repository.NotificationRepository

class CancelApplicationUseCase(
    private val appRepo: ApplicationRepository,
    private val notifRepo: NotificationRepository,
) {
    suspend operator fun invoke(
        applicationId: String,
        eventId: String
    ): Result<Unit> {
        val result = appRepo.cancel(applicationId)
        if (result is Result.Success) {
            notifRepo.cancelReminder(eventId)
        }
        return result
    }
}