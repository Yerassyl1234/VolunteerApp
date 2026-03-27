package org.example.volunteer.presentation.screens.mainevent

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.volunteer.core.ui.*
import org.example.volunteer.presentation.screens.mainevent.components.CategoryChip
import org.example.volunteer.presentation.screens.mainevent.components.EventCard
import org.example.volunteer.presentation.screens.mainevent.components.EventSearchBar
import org.example.volunteer.presentation.screens.mainevent.components.UrgentEventCard
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
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
        containerColor = PremiumBackground,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            Surface(
                color = PremiumBackground,
                shadowElevation = 0.dp
            ) {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
                    EventSearchBar(
                        searchQuery = state.searchQuery,
                        onSearchQueryChanged = { vm.onIntent(MainEventAction.SearchEvent(it)) },
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                    )
                    LazyRow(
                        modifier = Modifier.padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
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
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            state.urgentEvent?.let { urgent ->
                item {
                    Text(
                        text = stringResource(Res.string.urgent_help_title),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = PremiumDarkText,
                        fontSize = 22.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    UrgentEventCard(event = urgent) {
                        vm.onIntent(MainEventAction.NavigateToEventDetails(urgent.id))
                    }
                }
            }

            item {
                Text(
                    text = stringResource(Res.string.recommended_events_title),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = PremiumDarkText,
                    fontSize = 22.sp
                )
            }

            if (state.isLoading) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = LightGreen)
                    }
                }
            } else if (state.errorMessage != null) {
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = state.errorMessage!!.asString(),
                            color = PremiumRed,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(Modifier.height(16.dp))
                        Button(
                            onClick = { vm.onIntent(MainEventAction.Retry) },
                            colors = ButtonDefaults.buttonColors(containerColor = PremiumRedLight, contentColor = PremiumRed),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(stringResource(Res.string.retry_button), fontWeight = FontWeight.Bold)
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

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}
