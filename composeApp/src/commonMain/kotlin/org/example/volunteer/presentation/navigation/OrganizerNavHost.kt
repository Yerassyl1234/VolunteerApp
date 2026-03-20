package org.example.volunteer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun OrganizerNavHost(onLogout: () -> Unit) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = OrgMyEventsRoute
    ) {
        composable<OrgMyEventsRoute> {
            Sample("Organizer · My Events")
        }
        composable<ManageEventRoute> {
            Sample("Manage Event")
        }
        composable<OrgAICreateRoute> {
            Sample("AI Create")
        }
        composable<OrgChatListRoute> {
            Sample("Chat List")
        }
        composable<ChatRoomRoute> {
            Sample("Chat Room")
        }
        composable<OrgProfileRoute> {
            Sample("Organizer · Profile")
        }
    }
}