package org.example.volunteer.presentation.screens.login

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.volunteer.core.common.handle
import org.example.volunteer.core.common.toUiText
import org.example.volunteer.domain.usecase.LoginUseCase
import org.example.volunteer.presentation.BaseViewModel

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : BaseViewModel<LoginUiState, LoginAction, LoginEffect>(
    initialState = LoginUiState()
) {
    override fun onIntent(intent: LoginAction) {
        when (intent) {
            is LoginAction.InputEmail -> updateState { copy(email = intent.email) }
            is LoginAction.InputPassword -> updateState { copy(password = intent.password) }
            LoginAction.LoginButton -> login()
            LoginAction.SwitchToRegistration -> sendEffect(LoginEffect.NavigateToRegistration)
        }
    }

    private fun login() = viewModelScope.launch {
        if (!state.value.canSubmit) return@launch
        updateState { copy(isLoading = true) }
        val s = state.value
        loginUseCase(s.email,s.password).handle(
            onSuccess = {
                updateState { copy(isLoading = false) }
                sendEffect(LoginEffect.NavigateToHome(it))
            },
            onError = {
                updateState { copy(isLoading = false) }
                sendEffect(LoginEffect.ShowError(it.toUiText()))
            }
        )
    }
}
