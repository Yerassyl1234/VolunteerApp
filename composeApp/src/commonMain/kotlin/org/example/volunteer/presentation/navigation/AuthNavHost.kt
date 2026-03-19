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
