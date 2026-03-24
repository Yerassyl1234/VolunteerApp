package org.example.volunteer.domain.usecase

import org.example.volunteer.core.common.NetworkResult
import org.example.volunteer.domain.repository.ApplicationRepository
import org.example.volunteer.domain.repository.NotificationRepository

class CancelApplicationUseCase(
    private val appRepo: ApplicationRepository,
    private val notifRepo: NotificationRepository,
) {
    suspend operator fun invoke(
        applicationId: String,
        eventId: String
    ): NetworkResult<Unit> {
        return when (
            val result = appRepo.cancel(applicationId)) {
            is NetworkResult.Success -> {
                notifRepo.cancelReminder(eventId)
                result
            }
            is NetworkResult.Loading -> result
            is NetworkResult.Error -> result
        }
    }
}