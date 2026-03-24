package org.example.volunteer.presentation.screens.createevent

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

sealed interface CreateEventAction{
    data class InputEventTitle(val title: String) : CreateEventAction
    data class SetDate(val date: LocalDate?) : CreateEventAction
    data class InputDescription(val description: String) : CreateEventAction
    data class AddRequirement(val requirement: String) : CreateEventAction
    data class SetLocation(val location: String) : CreateEventAction
    data class SetTime(val time: LocalTime?) : CreateEventAction
    data class RemoveRequirement(val id: String) : CreateEventAction
    object GenerateImage : CreateEventAction
    object RegenerateImage : CreateEventAction
    object BackButtonClick : CreateEventAction
    object PublishEvent : CreateEventAction
}