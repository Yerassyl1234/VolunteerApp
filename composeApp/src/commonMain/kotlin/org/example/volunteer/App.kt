package org.example.volunteer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.volunteer.domain.entity.UserRole
import org.example.volunteer.presentation.AppState
import org.example.volunteer.presentation.AppViewModel
import org.example.volunteer.presentation.navigation.AuthNavHost
import org.example.volunteer.presentation.navigation.OrganizerNavHost
import org.example.volunteer.presentation.navigation.VolunteerNavHost
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App(appViewModel: AppViewModel = koinViewModel()) {
    MaterialTheme {
        val state by appViewModel.state.collectAsStateWithLifecycle()

        when (val current = state) {
            AppState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            AppState.NotAuthorized -> {
                AuthNavHost(onAuthSuccess = appViewModel::onAuthSuccess)
            }

            is AppState.Authorized -> {
                when (current.role) {
                    UserRole.VOLUNTEER -> VolunteerNavHost(onLogout = appViewModel::onLogout)
                    UserRole.ORGANIZER -> OrganizerNavHost(onLogout = appViewModel::onLogout)
                }
            }
        }
    }
}
