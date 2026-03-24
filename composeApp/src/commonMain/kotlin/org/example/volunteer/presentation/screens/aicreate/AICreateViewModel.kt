package org.example.volunteer.presentation.screens.aicreate

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.volunteer.domain.usecase.CreateEventUseCase
import org.example.volunteer.presentation.BaseViewModel
import org.example.volunteer.core.common.Result
import org.example.volunteer.core.common.toUiText

class AICreateViewModel(
    private val createEvent: CreateEventUseCase,
) : BaseViewModel<AICreateUiState, AICreateAction, AICreateEffect>(
    initialState = AICreateUiState()
) {
    override fun onIntent(intent: AICreateAction) {
        when (intent) {
            is AICreateAction.PromptChanged -> updateState { copy(prompt = intent.text) }
            AICreateAction.Generate -> generate()
            is AICreateAction.UpdateTitle -> updateState { copy(draft = draft.copy(title = intent.title)) }
            is AICreateAction.UpdateDescription -> updateState { copy(draft = draft.copy(description = intent.desc)) }
            AICreateAction.Publish -> publish()
            AICreateAction.SaveDraft -> saveDraft()
        }
    }

    private fun generate() = viewModelScope.launch {
        if (!state.value.canGenerate) return@launch
        updateState { copy(isGenerating = true) }
        // TODO сделать вызов с иирепоизтория
        updateState { copy(isGenerating = false, step = AICreateStep.RESULT) }
    }

    private fun publish() = viewModelScope.launch {
        if (!state.value.canPublish) return@launch
        updateState { copy(isPublishing = true) }
        when (val result = createEvent(state.value.draft)) {
            is Result.Success -> sendEffect(AICreateEffect.NavigateToMyEvents)
            is Result.Error -> {
                updateState { copy(isPublishing = false) }
                sendEffect(AICreateEffect.ShowError(result.exception.toUiText()))
            }
        }
    }

    private fun saveDraft() = viewModelScope.launch {
        // TODO может удалю походу рефакторинга
    }
}