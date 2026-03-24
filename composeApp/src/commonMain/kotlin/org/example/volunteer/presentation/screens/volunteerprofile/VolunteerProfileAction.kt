package org.example.volunteer.presentation.screens.volunteerprofile

sealed interface VolunteerProfileAction {
    data class SetAvatar(val image: String) : VolunteerProfileAction
    object Logout : VolunteerProfileAction
    object NotificationSettings : VolunteerProfileAction
    object EditProfile : VolunteerProfileAction
}