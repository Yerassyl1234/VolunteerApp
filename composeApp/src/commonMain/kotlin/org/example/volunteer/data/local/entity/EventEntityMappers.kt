package org.example.volunteer.data.local.entity

import org.example.volunteer.domain.entity.*
import kotlinx.datetime.Clock

fun EventEntity.toEvent() = Event(
    id = id,
    title = title,
    description = description,
    category = try { Category.valueOf(category) } catch (_: Exception) { Category.ECOLOGY },
    location = Location(
        name = locationName,
        address = locationAddress,
        latitude = latitude,
        longitude = longitude,
    ),
    dateMs = dateMs,
    durationHours = durationHours,
    totalSlots = totalSlots,
    takenSlots = takenSlots,
    organizerId = organizerId,
    organizerName = organizerName,
    organizerAvatarUrl = organizerAvatarUrl,
    isOrganizerVerified = isOrganizerVerified,
    coverUrl = coverUrl,
    isUrgent = isUrgent,
    isFree = isFree,
    price = price,
    status = try { EventStatus.valueOf(status) } catch (_: Exception) { EventStatus.ACTIVE },
)

fun Event.toEntity() = EventEntity(
    id = id,
    title = title,
    description = description,
    category = category.name,
    locationName = location.name,
    locationAddress = location.address,
    latitude = location.latitude,
    longitude = location.longitude,
    dateMs = dateMs,
    durationHours = durationHours,
    totalSlots = totalSlots,
    takenSlots = takenSlots,
    organizerId = organizerId,
    organizerName = organizerName,
    organizerAvatarUrl = organizerAvatarUrl,
    isOrganizerVerified = isOrganizerVerified,
    coverUrl = coverUrl,
    isUrgent = isUrgent,
    isFree = isFree,
    price = price,
    status = status.name,
    cachedAtMs = Clock.System.now().toEpochMilliseconds(),
)