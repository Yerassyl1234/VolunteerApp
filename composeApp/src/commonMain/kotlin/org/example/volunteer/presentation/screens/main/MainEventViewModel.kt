package org.example.volunteer.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainEventViewModel(

): ViewModel() {

    private val _state= MutableStateFlow(MainEventUiState())
    val state=_state.asStateFlow()

    private val _effects=Channel<MainEventEffect>()
    val effects=_effects.receiveAsFlow()

    init {
        onAction(MainEventAction.LoadInitialData)
    }

    fun onAction(action: MainEventAction){
        when(action) {
            is MainEventAction.CategorySelected -> TODO()
            MainEventAction.LoadInitialData -> loadAll()
            is MainEventAction.NavigateToEventDetails -> TODO()
            MainEventAction.Retry -> loadAll()
            is MainEventAction.SearchEvent -> _state.update { it.copy(searchQuery = action.query) }
            is MainEventAction.ToggleNotification -> TODO()
        }

    }

    private fun loadAll(){
        viewModelScope.launch {  }
    }
}