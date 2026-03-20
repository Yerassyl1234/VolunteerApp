package org.example.volunteer.presentation.screens.volunteerprofile

sealed interface VolunteerProfileAction {
    data object Logout : VolunteerProfileAction
    data object NotificationSettings : VolunteerProfileAction
    data class SetAvatar(val image: String) : VolunteerProfileAction
    data object EditProfile : VolunteerProfileAction
}