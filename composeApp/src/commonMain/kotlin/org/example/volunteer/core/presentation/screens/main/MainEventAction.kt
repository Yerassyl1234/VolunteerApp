package org.example.volunteer.core.presentation.screens.main

import org.example.volunteer.core.domain.entity.Category
import org.example.volunteer.core.domain.entity.Event

sealed interface MainEventAction{
    data object LoadInitialData: MainEventAction
    data class SearchEvent(val query:String): MainEventAction
    data class CategorySelected(val category: Category?): MainEventAction
    data class ToggleNotification(val enabled: Boolean): MainEventAction
    data class NavigateToEventDetails(val eventId: String): MainEventAction
    data object Retry: MainEventAction
}