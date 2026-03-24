package org.example.volunteer.presentation.screens.orgprofile

sealed interface OrgProfileAction {
    object Logout : OrgProfileAction
    object EditProfile : OrgProfileAction
    object NotificationSettings : OrgProfileAction
}