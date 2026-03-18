package org.example.volunteer.presentation.screens.main

import androidx.compose.runtime.Immutable
import org.example.volunteer.core.ui.UiText
import org.example.volunteer.domain.entity.Category
import org.example.volunteer.domain.entity.Event

@Immutable
data class MainEventUiState(
    val userName: String = "",
    val searchQuery: String = "",
    val category: List<Category> = emptyList(),
    val selectedCategory: Category? = null,
    val urgentEvent: Event? = null,
    val recommendedEvents: List<Event> = emptyList(),
    val isLoading: Boolean = false,
    val isUrgentEventLoading: Boolean = false,
    val isNotificationEnabled: Boolean = false,
    val errorMessage: UiText? = null
)


