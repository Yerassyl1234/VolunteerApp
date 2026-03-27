package org.example.volunteer.presentation.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.volunteer.core.ui.*
import org.example.volunteer.domain.entity.UserRole
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import volunteerapp.composeapp.generated.resources.Res
import volunteerapp.composeapp.generated.resources.*

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

    Scaffold(
        containerColor = PremiumBackground,
        snackbarHost = { SnackbarHost(snackbar) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(Res.string.welcome_title),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = PremiumDarkText
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Войдите, чтобы продолжить",
                style = MaterialTheme.typography.bodyLarge,
                color = PremiumGrayText,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            OutlinedTextField(
                value = state.email,
                onValueChange = { vm.onIntent(LoginAction.InputEmail(it)) },
                label = { Text(stringResource(Res.string.email_hint)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = PremiumSurface,
                    unfocusedContainerColor = PremiumSurface,
                    focusedBorderColor = LightGreen,
                    unfocusedBorderColor = Color.Transparent
                ),
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = PremiumGrayText) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                singleLine = true
            )
            
            Spacer(Modifier.height(16.dp))
            
            OutlinedTextField(
                value = state.password,
                onValueChange = { vm.onIntent(LoginAction.InputPassword(it)) },
                label = { Text(stringResource(Res.string.password_hint)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = PremiumSurface,
                    unfocusedContainerColor = PremiumSurface,
                    focusedBorderColor = LightGreen,
                    unfocusedBorderColor = Color.Transparent
                ),
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = PremiumGrayText) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                singleLine = true
            )
            
            Spacer(Modifier.height(32.dp))
            
            Button(
                onClick = { vm.onIntent(LoginAction.LoginButton) },
                enabled = state.canSubmit && !state.isLoading,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightGreen,
                    disabledContainerColor = PremiumDivider
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
            ) {
                if (state.isLoading) CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                else Text(stringResource(Res.string.login_button), fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
            
            Spacer(Modifier.height(24.dp))
            
            TextButton(
                onClick = { vm.onIntent(LoginAction.SwitchToRegistration) }
            ) {
                Text(
                    text = stringResource(Res.string.no_account),
                    color = LightGreen,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}