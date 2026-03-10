package org.example.volunteer.core.presentation.screens.createevent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class CreateEventViewModel : ViewModel() {
    private val _state = MutableStateFlow<CreateEventUIState>(CreateEventUIState())
    val state = _state.asStateFlow()
    private var imageVariation=0
    fun onAction(action: CreateEventAction) {
        when (action) {
            is CreateEventAction.InputEventTitle -> _state.update { it.copy(title = action.title) }
            is CreateEventAction.InputDescription -> _state.update { it.copy(description = action.description) }
            is CreateEventAction.SetDate -> _state.update { it.copy(date = action.date) }
            is CreateEventAction.SetTime -> _state.update { it.copy(time = action.time) }
            is CreateEventAction.SetLocation -> _state.update { it.copy(location = action.location) }

            is CreateEventAction.AddRequirement -> addRequirement(action.requirement)
            is CreateEventAction.RemoveRequirement -> removeRequirement(action.id)

            CreateEventAction.GenerateImage -> generateImage(regenerate = false)
            CreateEventAction.RegenerateImage -> generateImage(regenerate = true)

            CreateEventAction.BackButtonClick -> Unit
            CreateEventAction.PublishEvent -> Unit
        }
    }

    private fun addRequirement(text: String) {
        val t = text.trim()
        if (t.isBlank()) return
        _state.update { current ->
            current.copy(
                requirements = current.requirements + RequirementItem(text = t)
            )
        }
    }

    private fun removeRequirement(id: String) {
        _state.update { current ->
            current.copy(
                requirements = current.requirements.filterNot { it.id == id }
            )
        }
    }

    private fun generateImage(regenerate: Boolean) {
        val s = _state.value
        val canGenerate =
            s.title.isNotBlank() && s.description.isNotBlank() && s.location.isNotBlank()
        if (!canGenerate) {
            _state.update { it.copy(image = ImageState.Error("Заполни все нормально ")) }
            return
        }
        viewModelScope.launch {
            if(regenerate) imageVariation++
            _state.update { it.copy(image = ImageState.Generating) }
            //TODO success and error variation
        }
    }
    private fun publishEvent(){

    }
}