package org.example.volunteer.presentation.screens.mainevent

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.example.volunteer.core.common.asNetworkResult
import org.example.volunteer.core.common.toUiText
import org.example.volunteer.domain.entity.Category
import org.example.volunteer.domain.entity.EventFilter
import org.example.volunteer.domain.repository.EventRepository
import org.example.volunteer.presentation.BaseViewModel

class MainEventViewModel(
    private val eventRepository: EventRepository,
) : BaseViewModel<MainEventUiState, MainEventAction, MainEventEffect>(
    initialState = MainEventUiState(category = Category.entries)
) {
    private var eventsJob: Job? = null
    private var urgentJob: Job? = null

    init {
        onIntent(MainEventAction.LoadInitialData)
    }

    override fun onIntent(intent: MainEventAction) {
        when (intent) {
            MainEventAction.LoadInitialData -> loadAll()
            MainEventAction.Retry -> loadAll()
            is MainEventAction.SearchEvent -> {
                updateState { copy(searchQuery = intent.query) }
                loadEvents()
            }
            is MainEventAction.CategorySelected -> {
                updateState { copy(selectedCategory = intent.category) }
                loadEvents()
            }
            is MainEventAction.NavigateToEventDetails -> {
                sendEffect(MainEventEffect.OpenDetail(intent.eventId))
            }
            is MainEventAction.ToggleNotification -> {
                updateState { copy(isNotificationEnabled = intent.enabled) }
            }
        }
    }

    private fun loadAll() {
        loadEvents()
        loadUrgentEvent()
    }

    private fun loadEvents() {
        eventsJob?.cancel()
        val filter = EventFilter(
            category = state.value.selectedCategory,
            query = state.value.searchQuery,
        )
        eventsJob = eventRepository.getEvents(filter)
            .asNetworkResult()
            .onEach { result ->
                updateState {
                    copy(
                        isLoading = result.isLoading,
                        recommendedEvents = result.getOrNull()?:recommendedEvents,
                        errorMessage = result.exceptionOrNull()?.toUiText(),
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun loadUrgentEvent() {
        urgentJob?.cancel()
        urgentJob = eventRepository.getUrgentEvent()
            .asNetworkResult()
            .onEach { result ->
                updateState {
                    copy(
                        isUrgentEventLoading = result.isLoading,
                        urgentEvent = result.getOrNull()?:urgentEvent,
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}