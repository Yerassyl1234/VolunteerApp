package org.example.volunteer.presentation.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.volunteer.domain.entity.UserRole
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    onAuthSuccess: (UserRole) -> Unit,
    onNavigateToRegistration: () -> Unit,
    vm: LoginViewModel = koinViewModel(),
) {
    val state by vm.state.collectAsStateWithLifecycle()
    val snackbar = remember { SnackbarHostState() }
    LaunchedEffect(Unit) {
        vm.effects.collect { effect ->
            when (effect) {
                is LoginEffect.NavigateToHome -> onAuthSuccess(effect.role)
                LoginEffect.NavigateToRegistration -> onNavigateToRegistration()
                is LoginEffect.ShowError -> snackbar.showSnackbar(effect.message.asStringAsync())
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
                value = state.email,
                onValueChange = { vm.onIntent(LoginAction.InputEmail(it)) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(8.dp))
            TextField(
                value = state.password,
                onValueChange = { vm.onIntent(LoginAction.InputPassword(it)) },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { vm.onIntent(LoginAction.LoginButton) },
                enabled = state.canSubmit,
                modifier = Modifier.fillMaxWidth(),
            ) {
                if (state.isLoading) CircularProgressIndicator(modifier = Modifier.size(20.dp))
                else Text("Войти")
            }
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = { vm.onIntent(LoginAction.SwitchToRegistration) }) {
                Text("Нет аккаунта? Зарегистрироваться")
            }
        }
    }
}