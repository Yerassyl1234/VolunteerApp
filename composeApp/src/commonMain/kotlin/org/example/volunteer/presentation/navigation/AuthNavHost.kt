package org.example.volunteer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.volunteer.domain.entity.UserRole

@Composable
fun AuthNavHost(onAuthSuccess: (UserRole) -> Unit) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LoginRoute) {
        composable<LoginRoute> {
            Sample("Login")
        }
        composable<RegistrationRoute> {
            Sample("Registration")
        }
    }
}