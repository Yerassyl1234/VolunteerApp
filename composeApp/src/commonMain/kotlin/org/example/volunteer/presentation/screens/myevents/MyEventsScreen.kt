package org.example.volunteer.presentation.screens.myevents

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.example.volunteer.core.ui.*
import org.example.volunteer.domain.entity.Event
import org.example.volunteer.presentation.screens.mainevent.components.EventCard
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import volunteerapp.composeapp.generated.resources.Res
import volunteerapp.composeapp.generated.resources.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyEventsScreen(
    onEventClick: (String) -> Unit,
    vm: MyEventsViewModel = koinViewModel(),
) {
    val state by vm.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        vm.effects.collect { effect ->
            when (effect) {
                is MyEventsEffect.NavigateToDetail -> onEventClick(effect.eventId)
                is MyEventsEffect.ShowError -> snackbarHostState.showSnackbar(effect.message.asStringAsync())
                is MyEventsEffect.CancellationSuccess -> snackbarHostState.showSnackbar("Заявка отменена")
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
                Column(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(Res.string.my_events_title),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = PremiumDarkText,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val tabs = listOf(
                stringResource(Res.string.tab_upcoming),
                stringResource(Res.string.tab_archive)
            )
            val pagerState = rememberPagerState(pageCount = { tabs.size })
            val coroutineScope = rememberCoroutineScope()

            TabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = PremiumBackground,
                contentColor = PremiumDarkText,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        color = LightGreen,
                        height = 3.dp
                    )
                },
                divider = {
                    HorizontalDivider(color = PremiumDivider, thickness = 1.dp)
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    val selected = pagerState.currentPage == index
                    Tab(
                        selected = selected,
                        onClick = {
                            coroutineScope.launch { pagerState.animateScrollToPage(index) }
                        },
                        text = {
                            Text(
                                text = title,
                                fontSize = 16.sp,
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                                color = if (selected) LightGreen else PremiumGrayText
                            )
                        }
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                val events = if (page == 0) state.upcomingEvents else state.archiveEvents
                
                Crossfade(targetState = state.isLoading, label = "loading_crossfade") { isLoading ->
                    if (isLoading) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = LightGreen)
                        }
                    } else if (events.isEmpty() && state.error == null) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(
                                text = stringResource(Res.string.empty_events),
                                color = PremiumGrayText,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else if (state.error != null && events.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(
                                text = state.error!!.asString(),
                                color = PremiumRed,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(20.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(events) { event ->
                                EventCard(event = event) {
                                    vm.onIntent(MyEventsAction.NavigateToDetail(event.id))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
