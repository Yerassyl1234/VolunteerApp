package org.example.volunteer.domain.entity

data class EventApplication(
    val id: String,
    val eventId: String,
    val volunteerId: String,
    val volunteerName: String,
    val volunteerAvatarUrl: String?,
    val eventsAttended: Int,
    val status: ApplicationStatus
)
