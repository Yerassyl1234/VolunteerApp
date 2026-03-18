package org.example.volunteer.presentation.screens.main


import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.volunteer.domain.repository.EventRepository
import org.example.volunteer.presentation.BaseViewModel

class MainEventViewModel(
    private val eventRepository: EventRepository
) : BaseViewModel<MainEventUiState, MainEventAction, MainEventEffect>(
    initialState = MainEventUiState()
) {

    init {
        onIntent(MainEventAction.LoadInitialData)
    }

    override fun onIntent(intent: MainEventAction) {
        when (intent) {
            is MainEventAction.CategorySelected -> TODO()
            MainEventAction.LoadInitialData -> loadAll()
            is MainEventAction.NavigateToEventDetails -> TODO()
            MainEventAction.Retry -> loadAll()
            is MainEventAction.SearchEvent -> updateState { copy(searchQuery = intent.query) }
            is MainEventAction.ToggleNotification -> TODO()
        }

    }

    private fun loadAll() {
        viewModelScope.launch { }
    }
}