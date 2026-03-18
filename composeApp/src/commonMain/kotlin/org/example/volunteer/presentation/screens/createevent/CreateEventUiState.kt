package org.example.volunteer.presentation.screens.createevent

import androidx.compose.runtime.Immutable
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.example.volunteer.domain.entity.EventDraft
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Immutable
data class CreateEventUIState(
    val title: String = "",
    val date: LocalDate? = null,
    val time: LocalTime? = null,
    val description: String = "",
    val location: String = "",
    val requirements: List<RequirementItem> = emptyList(),
    val image: ImageState = ImageState.None,
    val isSaveEnabled: Boolean = true,
    val isLoading: Boolean = false
) {
    val isPublishEnabled: Boolean
        get() = title.isNotBlank() &&
                description.isNotBlank() &&
                location.isNotBlank() &&
                date != null &&
                time != null

    fun toDraft(): EventDraft = EventDraft(
        title = title,
        description = description,
        category = null,
        location = null,
        dateMs = null,
        totalSlots = 0,
        coverUrl = (image as? ImageState.Ready)?.url,
    )
}



data class RequirementItem @OptIn(ExperimentalUuidApi::class) constructor(
    val id: String= Uuid.random().toString(),
    val text: String,
)

sealed interface ImageState {
    data object None : ImageState
    data object Generating : ImageState
    data class Ready(val url: String) : ImageState
    data class Error(val message: String) : ImageState
}