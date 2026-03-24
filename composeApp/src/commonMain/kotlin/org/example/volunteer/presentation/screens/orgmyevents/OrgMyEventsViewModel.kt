package org.example.volunteer.presentation.screens.orgmyevents

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.volunteer.domain.entity.EventFilter
import org.example.volunteer.domain.entity.EventStatus
import org.example.volunteer.domain.repository.EventRepository
import org.example.volunteer.presentation.BaseViewModel

class OrgMyEventsViewModel(
    private val eventRepository: EventRepository,
) : BaseViewModel<OrgMyEventsUiState, OrgMyEventsAction, OrgMyEventsEffect>(
    initialState = OrgMyEventsUiState()
) {
    init {
        onIntent(OrgMyEventsAction.LoadInitialData)
    }

    override fun onIntent(intent: OrgMyEventsAction) {
        when (intent) {
            OrgMyEventsAction.LoadInitialData -> loadAll()
            is OrgMyEventsAction.NavigateToManage -> sendEffect(
                OrgMyEventsEffect.NavigateToManage(
                    intent.eventId
                )
            )

            is OrgMyEventsAction.NavigateToDetail -> sendEffect(
                OrgMyEventsEffect.NavigateToDetail(
                    intent.eventId
                )
            )
        }
    }

    private fun loadAll() {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            eventRepository.getEvents(EventFilter()).collect { events ->
                updateState {
                    copy(
                        activeEvents = events.filter { it.status == EventStatus.ACTIVE },
                        archiveEvents = events.filter { it.status == EventStatus.ARCHIVED },
                        isLoading = false,
                    )
                }
            }
        }
    }
}