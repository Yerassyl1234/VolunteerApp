package org.example.volunteer.core.presentation.screens.auth.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegistrationViewModel : ViewModel() {
    private val _state = MutableStateFlow<RegistrationUiState>(RegistrationUiState())
    val state = _state.asStateFlow()

    private val _effects=Channel<RegistrationEffect>()
    val effects=_effects.receiveAsFlow()

    fun onAction(action: RegistrationAction) {
        when (action) {
            is RegistrationAction.InputEmail -> {
                _state.update { it.copy(email = action.email) }
            }

            is RegistrationAction.InputPassword -> {
                _state.update { it.copy(password = action.password) }
            }

            is RegistrationAction.InputUserName -> {
                _state.update { it.copy(name = action.name) }
            }

            RegistrationAction.RegistrationButton -> TODO()

            is RegistrationAction.SelectRole -> {
                _state.update { it.copy(selectedRole = action.role) }
            }

            RegistrationAction.SwitchToLogin ->
                viewModelScope.launch {
                    _effects.send(RegistrationEffect.NavigateToLogin)
                }
        }
    }
}