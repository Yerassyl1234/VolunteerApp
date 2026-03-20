package org.example.volunteer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun VolunteerNavHost(onLogout: () -> Unit) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = VolunteerEventsRoute
    ) {
        composable<VolunteerEventsRoute> {
            Sample("Volunteer · Events")
        }
        composable<EventDetailRoute> {
            Sample("Event Detail")
        }
        composable<VolunteerMyEventsRoute> {
            Sample("Volunteer · My Events")
        }
        composable<VolunteerProfileRoute> {
            Sample("Volunteer · Profile")
        }
    }
}