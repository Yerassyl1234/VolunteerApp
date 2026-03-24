package org.example.volunteer.presentation.screens.myevents

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.example.volunteer.core.common.asNetworkResult
import org.example.volunteer.core.common.handle
import org.example.volunteer.core.common.toUiText
import org.example.volunteer.domain.entity.EventFilter
import org.example.volunteer.domain.repository.EventRepository
import org.example.volunteer.domain.usecase.CancelApplicationUseCase
import org.example.volunteer.presentation.BaseViewModel

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
        eventRepository.getEvents(EventFilter())
            .asNetworkResult()
            .onEach { result ->
                val now = Clock.System.now().toEpochMilliseconds()
                val allEvents = result.getOrNull() ?: (state.value.upcomingEvents + state.value.archiveEvents)
                updateState {
                    copy(
                        isLoading = result.isLoading,
                        upcomingEvents = allEvents.filter { it.dateMs > now },
                        archiveEvents = allEvents.filter { it.dateMs <= now },
                    )
                }
                result.onError {
                    sendEffect(MyEventsEffect.ShowError(it.toUiText()))
                }
            }
            .launchIn(viewModelScope)
    }

    private fun cancel(applicationId: String, eventId: String) = viewModelScope.launch {
        cancelApplication(applicationId, eventId).handle(
            onSuccess = {
                updateState {
                    copy(upcomingEvents = upcomingEvents.filterNot { it.id == eventId })
                }
                sendEffect(MyEventsEffect.CancellationSuccess)
            },
            onError = { sendEffect(MyEventsEffect.ShowError(it.toUiText())) }
        )
    }
}