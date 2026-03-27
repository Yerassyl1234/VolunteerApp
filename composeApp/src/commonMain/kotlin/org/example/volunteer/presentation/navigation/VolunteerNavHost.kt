package org.example.volunteer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.example.volunteer.presentation.screens.mainevent.MainEventScreen

@Composable
fun VolunteerNavHost(onLogout: () -> Unit) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = VolunteerEventsRoute
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
            Sample("Volunteer · My Events")
        }
        composable<VolunteerProfileRoute> {
            Sample("Volunteer · Profile")
        }
    }
}