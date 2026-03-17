package org.example.volunteer.core.presentation.screens.main

import androidx.compose.runtime.Immutable
import org.example.volunteer.core.domain.entity.Category
import org.example.volunteer.core.domain.entity.Event

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


