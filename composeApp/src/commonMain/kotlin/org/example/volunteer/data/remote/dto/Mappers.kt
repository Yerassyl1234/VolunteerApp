package org.example.volunteer.data.remote.dto

import org.example.volunteer.domain.entity.*

fun AuthResponseDto.toDomain() = AuthResult(
    userId = userId,
    role = try { UserRole.valueOf(role) } catch (_: Exception) { UserRole.VOLUNTEER },
    accessToken = accessToken,
    refreshToken = refreshToken,
)

fun EventResponseDto.toDomain() = Event(
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

fun EventDraft.toRequestDto() = CreateEventRequestDto(
    title = title,
    description = description,
    category = category?.name ?: "",
    locationName = location?.name ?: "",
    locationAddress = location?.address ?: "",
    latitude = location?.latitude ?: 0.0,
    longitude = location?.longitude ?: 0.0,
    dateMs = dateMs ?: 0L,
    durationHours = durationHours,
    totalSlots = totalSlots,
    isFree = isFree,
    price = price,
    coverUrl = coverUrl,
)

fun EventDraftResponseDto.toDomain() = EventDraft(
    title = title,
    description = description,
    category = try { Category.valueOf(category) } catch (_: Exception) { null },
    location = Location(name = locationName, address = "", latitude = 0.0, longitude = 0.0),
    dateMs = dateMs,
    durationHours = durationHours,
    totalSlots = totalSlots,
    isFree = isFree,
)

fun ApplicationResponseDto.toDomain() = EventApplication(
    id = id,
    eventId = eventId,
    volunteerId = volunteerId,
    volunteerName = volunteerName,
    volunteerAvatarUrl = volunteerAvatarUrl,
    eventsAttended = eventsAttended,
    status = try { ApplicationStatus.valueOf(status) } catch (_: Exception) { ApplicationStatus.PENDING },
)

fun ApplicationShortResponseDto.toDomain() = EventApplication(
    id = id,
    eventId = eventId,
    volunteerId = volunteerId,
    volunteerName = "",
    volunteerAvatarUrl = null,
    eventsAttended = 0,
    status = try { ApplicationStatus.valueOf(status) } catch (_: Exception) { ApplicationStatus.PENDING },
)

fun ChatResponseDto.toDomain() = Chat(
    id = id,
    eventId = eventId,
    eventTitle = eventTitle,
    participantName = participantName,
    participantAvatarUrl = participantAvatarUrl,
    lastMessage = lastMessage,
    lastMessageMs = lastMessageMs,
    unreadCount = unreadCount,
)

fun MessageResponseDto.toDomain() = Message(
    id = id,
    chatId = chatId,
    senderId = senderId,
    senderName = senderName,
    text = text,
    timestampMs = timestampMs,
    isRead = isRead,
)

fun VolunteerProfileResponseDto.toDomain() = VolunteerProfile(
    id = id,
    name = name,
    avatarUrl = avatarUrl,
    email = email,
    eventsAttended = eventsAttended,
    hoursVolunteered = hoursVolunteered,
)

fun OrganizerProfileResponseDto.toDomain() = OrganizerProfile(
    id = id,
    name = name,
    avatarUrl = avatarUrl,
    email = email,
    isVerified = isVerified,
    eventsCreated = eventsCreated,
    totalVolunteersAttended = totalVolunteers,
)