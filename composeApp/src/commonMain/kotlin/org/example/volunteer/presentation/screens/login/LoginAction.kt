package org.example.volunteer.presentation.screens.login

sealed interface LoginAction {
    data class InputEmail(val email: String) : LoginAction
    data class InputPassword(val password: String) : LoginAction
    object LoginButton : LoginAction
    object SwitchToRegistration : LoginAction
}