package org.example.volunteer.presentation.screens.aicreate

sealed interface AICreateAction {
    data class PromptChanged(val text: String) : AICreateAction
    data class UpdateTitle(val title: String) : AICreateAction
    data class UpdateDescription(val desc: String) : AICreateAction
    object Publish : AICreateAction
    object SaveDraft : AICreateAction
    object Generate : AICreateAction
}