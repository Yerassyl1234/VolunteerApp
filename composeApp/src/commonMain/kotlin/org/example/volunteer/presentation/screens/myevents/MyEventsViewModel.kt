package org.example.volunteer.presentation.screens.myevents

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.volunteer.domain.entity.EventFilter
import org.example.volunteer.domain.repository.EventRepository
import org.example.volunteer.domain.usecase.CancelApplicationUseCase
import org.example.volunteer.presentation.BaseViewModel
import org.example.volunteer.core.common.Result
import kotlinx.datetime.Clock
import org.example.volunteer.core.common.toUiText

class MyEventsViewModel(
    private val eventRepository: EventRepository,
    private val cancelApplication: CancelApplicationUseCase,
) : BaseViewModel<MyEventsUiState, MyEventsAction, MyEventsEffect>(
    initialState = MyEventsUiState()
) {
    init {
        onIntent(MyEventsAction.LoadInitialData)
    }

    override fun onIntent(intent: MyEventsAction) {
        when (intent) {
            MyEventsAction.LoadInitialData -> loadAll()
            is MyEventsAction.CancelApplication -> cancel(intent.applicationId, intent.eventId)
            is MyEventsAction.NavigateToDetail -> sendEffect(MyEventsEffect.NavigateToDetail(intent.eventId))
        }
    }

    private fun loadAll() {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            eventRepository.getEvents(EventFilter()).collect { events ->
                val now = Clock.System.now().toEpochMilliseconds()
                updateState {
                    copy(
                        upcomingEvents = events.filter { it.dateMs > now },
                        archiveEvents = events.filter { it.dateMs <= now },
                        isLoading = false,
                    )
                }
            }
        }
    }

    private fun cancel(applicationId: String, eventId: String) = viewModelScope.launch {
        when (val result = cancelApplication(applicationId, eventId)) {
            is Result.Success -> {
                updateState {
                    copy(
                        upcomingEvents = upcomingEvents.filterNot { it.id == eventId }
                    )
                }
                sendEffect(MyEventsEffect.CancellationSuccess)
            }

            is Result.Error -> {
                sendEffect(MyEventsEffect.ShowError(result.exception.toUiText()))
            }
        }
    }
}