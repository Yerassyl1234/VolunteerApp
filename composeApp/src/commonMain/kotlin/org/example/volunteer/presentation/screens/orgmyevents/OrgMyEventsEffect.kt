package org.example.volunteer.presentation.screens.orgmyevents

import org.example.volunteer.core.ui.UiText

sealed interface OrgMyEventsEffect {
    data class NavigateToManage(val eventId: String) : OrgMyEventsEffect
    data class NavigateToDetail(val eventId: String) : OrgMyEventsEffect
    data class ShowError(val message: UiText) : OrgMyEventsEffect
}