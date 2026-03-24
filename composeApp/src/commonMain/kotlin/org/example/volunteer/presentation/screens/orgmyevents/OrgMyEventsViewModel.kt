package org.example.volunteer.presentation.screens.orgmyevents

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.example.volunteer.core.common.asNetworkResult
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
                OrgMyEventsEffect.NavigateToManage(intent.eventId)
            )
            is OrgMyEventsAction.NavigateToDetail -> sendEffect(
                OrgMyEventsEffect.NavigateToDetail(intent.eventId)
            )
        }
    }

    private fun loadAll() {
        eventRepository.getEvents(EventFilter())
            .asNetworkResult()
            .onEach { result ->
                val allEvents = result.getOrNull() ?: (state.value.activeEvents + state.value.archiveEvents)
                updateState {
                    copy(
                        isLoading = result.isLoading,
                        activeEvents = allEvents.filter { it.status == EventStatus.ACTIVE },
                        archiveEvents = allEvents.filter { it.status == EventStatus.ARCHIVED },
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}