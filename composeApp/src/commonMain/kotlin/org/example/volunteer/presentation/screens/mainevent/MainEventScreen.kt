package org.example.volunteer.presentation.screens.mainevent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.volunteer.presentation.screens.mainevent.components.CategoryChip
import org.example.volunteer.presentation.screens.mainevent.components.EventCard
import org.example.volunteer.presentation.screens.mainevent.components.EventSearchBar
import org.example.volunteer.presentation.screens.mainevent.components.UrgentEventCard
import org.jetbrains.compose.resources.stringResource
import org.koin.androidx.compose.koinViewModel
import volunteerapp.composeapp.generated.resources.Res
import volunteerapp.composeapp.generated.resources.recommended_events_title
import volunteerapp.composeapp.generated.resources.retry_button
import volunteerapp.composeapp.generated.resources.urgent_help_title

@Composable
fun MainEventScreen(
    onEventClick: (String) -> Unit,
    vm: MainEventViewModel = koinViewModel(),
) {
    val state by vm.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        vm.effects.collect { effect ->
            when (effect) {
                is MainEventEffect.OpenDetail -> onEventClick(effect.eventId)
                is MainEventEffect.ShowError -> {
                    snackbarHostState.showSnackbar(effect.message.asStringAsync())
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                EventSearchBar(
                    searchQuery = state.searchQuery,
                    onSearchQueryChanged = { vm.onIntent(MainEventAction.SearchEvent(it)) },
                    modifier = Modifier.padding(top = 8.dp)
                )
                LazyRow(
                    modifier = Modifier.padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.categories) { category ->
                        CategoryChip(
                            category = category,
                            isSelected = category == state.selectedCategory,
                            onSelect = { vm.onIntent(MainEventAction.CategorySelected(category)) }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            state.urgentEvent?.let { urgent ->
                item {
                    Text(
                        text = stringResource(Res.string.urgent_help_title),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(8.dp))
                    UrgentEventCard(event = urgent) {
                        vm.onIntent(MainEventAction.NavigateToEventDetails(urgent.id))
                    }
                }
            }

            item {
                Text(
                    text = stringResource(Res.string.recommended_events_title),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            if (state.isLoading) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            } else if (state.errorMessage != null) {
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = state.errorMessage!!.asString(),
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(Modifier.height(8.dp))
                        TextButton(onClick = { vm.onIntent(MainEventAction.Retry) }) {
                            Text(stringResource(Res.string.retry_button))
                        }
                    }
                }
            } else {
                items(state.recommendedEvents) { event ->
                    EventCard(event = event) {
                        vm.onIntent(MainEventAction.NavigateToEventDetails(event.id))
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}
