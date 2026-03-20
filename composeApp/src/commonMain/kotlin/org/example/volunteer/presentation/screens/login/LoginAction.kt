package org.example.volunteer.presentation.screens.login

sealed interface LoginAction {
    data class InputEmail(val email: String) : LoginAction
    data class InputPassword(val password: String) : LoginAction
    data object LoginButton : LoginAction
    data object SwitchToRegistration : LoginAction
}