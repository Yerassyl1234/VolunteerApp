package org.example.volunteer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val category: String,
    val locationName: String,
    val locationAddress: String,
    val latitude: Double,
    val longitude: Double,
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
    val status: String,
    val cachedAtMs: Long = 0L,
)