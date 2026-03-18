package org.example.volunteer.presentation.screens.createevent

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.volunteer.core.common.Result
import org.example.volunteer.core.common.toUiText
import org.example.volunteer.domain.repository.EventRepository
import org.example.volunteer.domain.usecase.CreateEventUseCase
import org.example.volunteer.presentation.BaseViewModel

class CreateEventViewModel(
    private val createEventUseCase: CreateEventUseCase,
    private val eventRepository: EventRepository
) : BaseViewModel<CreateEventUIState, CreateEventAction, CreateEventEffect>(
    initialState = CreateEventUIState()
) {

    override fun onIntent(intent: CreateEventAction) {
        when (intent) {
            is CreateEventAction.InputEventTitle   -> updateState { copy(title = intent.title) }
            is CreateEventAction.InputDescription  -> updateState { copy(description = intent.description) }
            is CreateEventAction.SetDate           -> updateState { copy(date = intent.date) }
            is CreateEventAction.SetTime           -> updateState { copy(time = intent.time) }
            is CreateEventAction.SetLocation       -> updateState { copy(location = intent.location) }
            is CreateEventAction.AddRequirement    -> addRequirement(intent.requirement)
            is CreateEventAction.RemoveRequirement -> removeRequirement(intent.id)
            CreateEventAction.GenerateImage        -> generateImage(regenerate = false)
            CreateEventAction.RegenerateImage      -> generateImage(regenerate = true)
            CreateEventAction.BackButtonClick      -> sendEffect(CreateEventEffect.NavigateBack)
            CreateEventAction.PublishEvent         -> publishEvent()
        }
    }

    private fun addRequirement(text: String) {
        val trimmed = text.trim()
        if (trimmed.isBlank()) return
        updateState {
            copy(requirements = requirements + RequirementItem(text = trimmed))
        }
    }

    private fun removeRequirement(id: String) {
        updateState {
            copy(requirements = requirements.filterNot { it.id == id })
        }
    }

    private fun generateImage(regenerate: Boolean) {
        val s = state.value
        if (s.title.isBlank() || s.description.isBlank() || s.location.isBlank()) {
            updateState { copy(image = ImageState.Error("Заполните все поля")) }
            return
        }
        viewModelScope.launch {
            updateState { copy(image = ImageState.Generating) }
            // TODO
        }
    }

    private fun publishEvent() = viewModelScope.launch {
        updateState { copy(isLoading = true) }
        when (val result = createEventUseCase(state.value.toDraft())) {
            is Result.Success -> {
                updateState { copy(isLoading = false) }
                sendEffect(CreateEventEffect.NavigateToMyEvents)
            }
            is Result.Error -> {
                updateState { copy(isLoading = false) }
                sendEffect(CreateEventEffect.ShowError(result.exception.toUiText()))
            }
        }
    }
}