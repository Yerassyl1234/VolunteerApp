package org.example.volunteer.domain.entity

data class Event(
    val id: String,
    val title: String,
    val description: String,
    val category: Category,
    val location: Location,
    val dateMs: Long,
    val durationHours: Int,
    val totalSlots: Int,
    val takenSlots: Int,
    val organizerId: String,
    val organizerName: String,
    val organizerAvatarUrl: String?,
    val isOrganizerVerified: Boolean,
    val coverUrl: String?,
    val isUrgent: Boolean,
    val isFree: Boolean,
    val price: Double?,
    val status: EventStatus
)