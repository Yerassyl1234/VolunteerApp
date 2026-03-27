package org.example.volunteer.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.example.volunteer.core.ui.LightGreen
import org.example.volunteer.core.ui.PremiumBackground
import org.example.volunteer.core.ui.PremiumDarkText
import org.example.volunteer.core.ui.PremiumGrayText
import org.example.volunteer.core.ui.PremiumSurface
import org.example.volunteer.presentation.screens.mainevent.MainEventScreen
import org.example.volunteer.presentation.screens.myevents.MyEventsScreen
import org.example.volunteer.presentation.screens.volunteerprofile.VolunteerProfileScreen
import org.jetbrains.compose.resources.stringResource
import volunteerapp.composeapp.generated.resources.Res
import volunteerapp.composeapp.generated.resources.nav_home
import volunteerapp.composeapp.generated.resources.nav_my_events
import volunteerapp.composeapp.generated.resources.nav_profile

data class BottomNavItem(
    val titleRes: org.jetbrains.compose.resources.StringResource,
    val icon: ImageVector,
    val route: String
)

@Composable
fun VolunteerNavHost(onLogout: () -> Unit) {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val items = listOf(
        BottomNavItem(Res.string.nav_home, Icons.Default.Home, VolunteerEventsRoute::class.qualifiedName ?: "VolunteerEventsRoute"),
        BottomNavItem(Res.string.nav_my_events, Icons.Default.Event, VolunteerMyEventsRoute::class.qualifiedName ?: "VolunteerMyEventsRoute"),
        BottomNavItem(Res.string.nav_profile, Icons.Default.Person, VolunteerProfileRoute::class.qualifiedName ?: "VolunteerProfileRoute")
    )

    val isBottomBarVisible = items.any { it.route == currentDestination?.route }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = PremiumBackground,
        bottomBar = {
            AnimatedVisibility(
                visible = isBottomBarVisible,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                NavigationBar(
                    containerColor = PremiumSurface,
                    contentColor = PremiumDarkText,
                    tonalElevation = 0.dp,
                    modifier = Modifier.shadow(
                        elevation = 16.dp, 
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                        spotColor = Color.Black.copy(alpha = 0.05f)
                    )
                ) {
                    items.forEach { item ->
                        val isSelected = currentDestination?.hierarchy?.any { it.route == item.route } == true
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().route ?: "") {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(item.icon, contentDescription = null) },
                            label = { Text(stringResource(item.titleRes)) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = LightGreen,
                                selectedTextColor = LightGreen,
                                unselectedIconColor = PremiumGrayText,
                                unselectedTextColor = PremiumGrayText,
                                indicatorColor = LightGreen.copy(alpha = 0.15f)
                            )
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = VolunteerEventsRoute,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable<VolunteerEventsRoute> {
                MainEventScreen(
                    onEventClick = { eventId ->
                        navController.navigate(EventDetailRoute(eventId))
                    }
                )
            }
            composable<EventDetailRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<EventDetailRoute>()
                Sample("Event Detail: ${route.eventId}")
            }
            composable<VolunteerMyEventsRoute> {
                MyEventsScreen(
                    onEventClick = { eventId ->
                        navController.navigate(EventDetailRoute(eventId))
                    }
                )
            }
            composable<VolunteerProfileRoute> {
                VolunteerProfileScreen(onLogout = onLogout)
            }
        }
    }
}