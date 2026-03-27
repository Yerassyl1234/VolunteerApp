package org.example.volunteer.presentation.screens.registration

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.volunteer.core.common.handle
import org.example.volunteer.core.common.toUiText
import org.example.volunteer.domain.usecase.RegisterUseCase
import org.example.volunteer.presentation.BaseViewModel

class RegistrationViewModel(
    private val registerUseCase: RegisterUseCase,
) : BaseViewModel<RegistrationUiState, RegistrationAction, RegistrationEffect>(
    initialState = RegistrationUiState()
) {

    override fun onIntent(intent: RegistrationAction) {
        when (intent) {
            is RegistrationAction.InputEmail ->
                updateState { copy(email = intent.email) }

            is RegistrationAction.InputPassword ->
                updateState { copy(password = intent.password) }

            is RegistrationAction.InputUserName ->
                updateState { copy(name = intent.name) }

            is RegistrationAction.SelectRole ->
                updateState { copy(selectedRole = intent.role) }

            RegistrationAction.RegistrationButton ->
                register()

            RegistrationAction.SwitchToLogin ->
                sendEffect(RegistrationEffect.NavigateToLogin)
        }
    }

    private fun register() = viewModelScope.launch {
        if (!state.value.canSubmit) return@launch
        updateState { copy(isLoading = true) }
        val s = state.value
        registerUseCase(s.name,s.email,s.password,s.selectedRole).handle(
            onSuccess = {
                updateState { copy(isLoading = false) }
                sendEffect(RegistrationEffect.NavigateToHome(it))
            },
            onError = {
                updateState { copy(isLoading = false) }
                sendEffect(RegistrationEffect.ShowError(it.toUiText()))
            }
        )
    }
}