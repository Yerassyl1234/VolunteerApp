package org.example.volunteer.presentation

import org.example.volunteer.domain.entity.UserRole

sealed interface AppState {
    data object Loading : AppState
    data object NotAuthorized : AppState
    data class Authorized(val role: UserRole) : AppState
}