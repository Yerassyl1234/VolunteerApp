package org.example.volunteer.presentation.screens.aicreate

import androidx.compose.runtime.Immutable
import org.example.volunteer.core.ui.UiText
import org.example.volunteer.domain.entity.EventDraft

enum class AICreateStep { PROMPT, RESULT }

@Immutable
data class AICreateUiState(
    val prompt: String = "",
    val draft: EventDraft = EventDraft(),
    val isGenerating: Boolean = false,
    val isPublishing: Boolean = false,
    val step: AICreateStep = AICreateStep.PROMPT,
    val error: UiText? = null,
) {
    val canGenerate get() = prompt.length >= 10 && !isGenerating
    val canPublish get() = draft.isValid && !isPublishing
}