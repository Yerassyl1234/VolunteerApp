package org.example.volunteer.domain.usecase

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import org.example.volunteer.core.common.NetworkResult
import org.example.volunteer.domain.entity.EventApplication
import org.example.volunteer.domain.repository.ApplicationRepository
import org.example.volunteer.domain.repository.EventRepository
import org.example.volunteer.domain.repository.NotificationRepository


class ApplyForEventUseCase(
    private val appRepo: ApplicationRepository,
    private val eventRepo: EventRepository,
    private val notifRepo: NotificationRepository,
) {
    suspend operator fun invoke(eventId: String): NetworkResult<EventApplication> {
        return when (val result = appRepo.apply(eventId)) {
            is NetworkResult.Success -> {
                try {
                    val event = eventRepo.getEventById(eventId).firstOrNull()
                    if (event != null) {
                        notifRepo.scheduleAllReminders(
                            eventId, event.title, event.dateMs
                        )
                    }
                } catch (_: Exception) {
                    // Event not found in cache — skip reminders
                }
                result
            }
            is NetworkResult.Error -> result
            is NetworkResult.Loading -> result
        }
    }
}