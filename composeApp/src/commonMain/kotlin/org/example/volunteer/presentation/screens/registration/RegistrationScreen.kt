package org.example.volunteer.presentation.screens.registration

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
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
                is RegistrationEffect.ShowError -> snackbar.showSnackbar(effect.message.asStringAsync())
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
                text = "Создать аккаунт",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = PremiumDarkText
            )
            Spacer(Modifier.height(32.dp))

            OutlinedTextField(
                value = state.name,
                onValueChange = { vm.onIntent(RegistrationAction.InputUserName(it)) },
                label = { Text(stringResource(Res.string.name_hint)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = PremiumSurface,
                    unfocusedContainerColor = PremiumSurface,
                    focusedBorderColor = LightGreen,
                    unfocusedBorderColor = Color.Transparent
                ),
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = PremiumGrayText) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            
            Spacer(Modifier.height(16.dp))
            
            OutlinedTextField(
                value = state.email,
                onValueChange = { vm.onIntent(RegistrationAction.InputEmail(it)) },
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
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
            )
            
            Spacer(Modifier.height(16.dp))
            
            OutlinedTextField(
                value = state.password,
                onValueChange = { vm.onIntent(RegistrationAction.InputPassword(it)) },
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
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done)
            )
            
            Spacer(Modifier.height(24.dp))
            
            // Premium Segmented Control for Role
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(PremiumSurface)
                    .padding(4.dp),
            ) {
                val isVol = state.selectedRole == UserRole.VOLUNTEER
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isVol) LightGreen else Color.Transparent)
                        .clickable { vm.onIntent(RegistrationAction.SelectRole(UserRole.VOLUNTEER)) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        stringResource(Res.string.role_volunteer),
                        color = if (isVol) Color.White else PremiumGrayText,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                val isOrg = state.selectedRole == UserRole.ORGANIZER
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isOrg) LightGreen else Color.Transparent)
                        .clickable { vm.onIntent(RegistrationAction.SelectRole(UserRole.ORGANIZER)) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        stringResource(Res.string.role_organizer),
                        color = if (isOrg) Color.White else PremiumGrayText,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            
            Spacer(Modifier.height(32.dp))
            
            Button(
                onClick = { vm.onIntent(RegistrationAction.RegistrationButton) },
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
                else Text(stringResource(Res.string.register_button), fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
            
            Spacer(Modifier.height(24.dp))
            
            TextButton(
                onClick = { vm.onIntent(RegistrationAction.SwitchToLogin) }
            ) {
                Text(
                    text = stringResource(Res.string.have_account),
                    color = LightGreen,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}