package org.example.volunteer.presentation.screens.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.volunteer.domain.entity.UserRole
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegistrationScreen(
    onAuthSuccess: (UserRole) -> Unit,
    onNavigateToLogin: () -> Unit,
    vm: RegistrationViewModel = koinViewModel(),
) {
    val state by vm.state.collectAsStateWithLifecycle()
    val snackbar = remember { SnackbarHostState() }
    LaunchedEffect(Unit) {
        vm.effects.collect { effect ->
            when (effect) {
                is RegistrationEffect.NavigateToHome -> onAuthSuccess(effect.role)
                RegistrationEffect.NavigateToLogin -> onNavigateToLogin()
                is RegistrationEffect.ShowError -> snackbar.showSnackbar(
                    effect.message.asStringAsync()
                )
            }
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbar) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextField(
                value = state.name,
                onValueChange = { vm.onIntent(RegistrationAction.InputUserName(it)) },
                label = { Text("Имя") },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(8.dp))
            TextField(
                value = state.email,
                onValueChange = { vm.onIntent(RegistrationAction.InputEmail(it)) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(8.dp))
            TextField(
                value = state.password,
                onValueChange = { vm.onIntent(RegistrationAction.InputPassword(it)) },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                FilterChip(
                    selected = state.selectedRole == UserRole.VOLUNTEER,
                    onClick = { vm.onIntent(RegistrationAction.SelectRole(UserRole.VOLUNTEER)) },
                    label = { Text("Волонтёр") },
                )
                FilterChip(
                    selected = state.selectedRole == UserRole.ORGANIZER,
                    onClick = { vm.onIntent(RegistrationAction.SelectRole(UserRole.ORGANIZER)) },
                    label = { Text("Организатор") },
                )
            }
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { vm.onIntent(RegistrationAction.RegistrationButton) },
                enabled = state.canSubmit,
                modifier = Modifier.fillMaxWidth(),
            ) {
                if (state.isLoading) CircularProgressIndicator(modifier = Modifier.size(20.dp))
                else Text("Зарегистрироваться")
            }
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = { vm.onIntent(RegistrationAction.SwitchToLogin) }) {
                Text("Уже есть аккаунт? Войти")
            }
        }
    }
}