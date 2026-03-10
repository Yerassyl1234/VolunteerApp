package org.example.volunteer.core.domain.entity

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Event @OptIn(ExperimentalUuidApi::class) constructor(
    val id: String = Uuid.random().toString(),
    val title: String,
    val description: String,
    val coverImageUrl: String,
    val category: Category,
    val isUrgent: Boolean?,
    val organizer: Profile,
    val date: LocalDate,
    val time: LocalTime,
    val durationInHours: Int,
    val totalSpots: Int,
    val occupiedSpots: Int,
    val participants: List<Profile>,
    val location: Location,
    val requirements: List<String>,
    val isFavorite: Boolean,
)