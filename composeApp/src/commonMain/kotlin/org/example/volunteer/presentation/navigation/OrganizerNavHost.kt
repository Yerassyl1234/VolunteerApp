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
            OrgMyEventScreen(

            )
        }
        composable<ManageEventRoute> {
            ManageEventScreen()
        }
        composable<OrgAICreateRoute> {
            AICreateScreen()
        }
        composable<OrgChatListRoute> {
            ChatListScreen()
        }
        composable<ChatRoomRoute> {
            ChatRoomScreen()
        }
        composable<OrgProfileRoute> {
            OrgProfileScreen()
        }
    }
}