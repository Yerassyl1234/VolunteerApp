package org.example.volunteer.presentation.screens.orgmyevents

sealed interface OrgMyEventsAction {
    data class NavigateToManage(val eventId: String) : OrgMyEventsAction
    data class NavigateToDetail(val eventId: String) : OrgMyEventsAction
    object LoadInitialData : OrgMyEventsAction
}