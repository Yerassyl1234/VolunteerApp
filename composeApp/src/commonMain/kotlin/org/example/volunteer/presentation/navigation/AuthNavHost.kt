package org.example.volunteer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.volunteer.domain.entity.UserRole
import org.example.volunteer.presentation.screens.login.LoginScreen
import org.example.volunteer.presentation.screens.registration.RegistrationScreen

@Composable
fun AuthNavHost(onAuthSuccess: (UserRole) -> Unit) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LoginRoute
    ) {
        composable<LoginRoute> {
            LoginScreen(
                onAuthSuccess=onAuthSuccess,
                onNavigateToRegistration = {
                    navController.navigate(RegistrationRoute)
                }
            )
        }
        composable<RegistrationRoute> {
            RegistrationScreen(
                onAuthSuccess = onAuthSuccess,
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }
    }
}