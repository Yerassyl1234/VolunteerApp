package org.example.volunteer.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable data object AuthGraph

@Serializable data object LoginRoute
@Serializable data object RegistrationRoute

@Serializable data object VolunteerGraph

@Serializable data object VolunteerEventsRoute
@Serializable data object VolunteerMyEventsRoute
@Serializable data object VolunteerProfileRoute
@Serializable data class  EventDetailRoute(val eventId: String)


@Serializable data object OrganizerGraph

@Serializable data object OrgMyEventsRoute
@Serializable data object OrgAICreateRoute
@Serializable data object OrgChatListRoute
@Serializable data object OrgProfileRoute
@Serializable data class  ManageEventRoute(val eventId: String)
@Serializable data class  ChatRoomRoute(val chatId: String)