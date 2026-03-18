package org.example.volunteer.presentation.screens.main

import org.example.volunteer.domain.entity.Category

sealed interface MainEventAction{
    data object LoadInitialData: MainEventAction
    data class SearchEvent(val query:String): MainEventAction
    data class CategorySelected(val category: Category?): MainEventAction
    data class ToggleNotification(val enabled: Boolean): MainEventAction
    data class NavigateToEventDetails(val eventId: String): MainEventAction
    data object Retry: MainEventAction
}