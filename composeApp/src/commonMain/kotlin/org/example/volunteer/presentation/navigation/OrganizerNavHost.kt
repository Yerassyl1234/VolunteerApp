package org.example.volunteer.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
