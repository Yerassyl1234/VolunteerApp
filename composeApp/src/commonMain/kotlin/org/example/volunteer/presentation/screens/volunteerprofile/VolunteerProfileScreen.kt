package org.example.volunteer.presentation.screens.volunteerprofile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import org.example.volunteer.core.ui.*
import org.example.volunteer.domain.entity.Badge
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import volunteerapp.composeapp.generated.resources.Res
import volunteerapp.composeapp.generated.resources.*

@Composable
fun VolunteerProfileScreen(
    onLogout: () -> Unit,
    viewModel: VolunteerProfileViewModel = koinViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is VolunteerProfileEffect.NavigateToLogin -> onLogout()
                else -> {} // Handle other effects if needed
            }
        }
    }
    val state by viewModel.state.collectAsState()
    val profile = state.profile

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is VolunteerProfileEffect.NavigateToEditProfile -> {}
                is VolunteerProfileEffect.NavigateToLogin -> {}
                is VolunteerProfileEffect.NavigateToNotificationSettings -> {}
                is VolunteerProfileEffect.ShowError -> {}
            }
        }
    }

    Scaffold(
        containerColor = PremiumBackground
    ) { paddingValues ->
        if (state.showLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = LightGreen)
            }
        } else if (profile != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                TopProfileBar(
                    city = stringResource(Res.string.city_almaty),
                    avatarUrl = profile.avatarUrl ?: ""
                )

                ProfileHeaderSection(
                    name = profile.name,
                    subtitle = profile.email,
                    avatarUrl = profile.avatarUrl ?: "",
                    onEditClick = { viewModel.onIntent(VolunteerProfileAction.EditProfile) }
                )

                Spacer(Modifier.height(32.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatCard(profile.eventsAttended.toString(), stringResource(Res.string.events_attended_label), Modifier.weight(1f))
                    StatCard(profile.hoursVolunteered.toString(), stringResource(Res.string.hours_volunteered_label), Modifier.weight(1f))
                    StatCard(profile.badges.size.toString(), stringResource(Res.string.badges_label), Modifier.weight(1f))
                }

                Spacer(Modifier.height(32.dp))

                if (profile.badges.isNotEmpty()) {
                    SectionHeader(stringResource(Res.string.achievements_title), onAllClick = { })
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(vertical = 12.dp)
                    ) {
                        items(profile.badges) { badge ->
                            BadgeItem(badge)
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                SettingsSection(viewModel)

                Spacer(Modifier.height(24.dp))

                LogoutButton(onClick = { viewModel.onIntent(VolunteerProfileAction.Logout) })

                Spacer(Modifier.height(32.dp))
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize().padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.ErrorOutline, contentDescription = null, tint = PremiumRed, modifier = Modifier.size(48.dp))
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = state.error?.asString() ?: "Не удалось загрузить профиль. Возможно, токен устарел или нет сети.",
                        color = PremiumGrayText,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(24.dp))
                    LogoutButton(onClick = { viewModel.onIntent(VolunteerProfileAction.Logout) })
                }
            }
        }
    }
}

@Composable
fun TopProfileBar(city: String, avatarUrl: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.LocationOn,
                contentDescription = null,
                tint = LightGreen,
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text(city, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = PremiumDarkText)
        }
        SubcomposeAsyncImage(
            model = avatarUrl.ifBlank { null },
            contentDescription = null,
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .border(2.dp, LightGreen, CircleShape),
            contentScale = ContentScale.Crop
        ) {
            when (painter.state) {
                is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
                else -> Box(
                    Modifier.fillMaxSize().background(PremiumDivider),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = PremiumGrayText, modifier = Modifier.size(24.dp))
                }
            }
        }
    }
}

@Composable
fun ProfileHeaderSection(
    name: String,
    subtitle: String,
    avatarUrl: String,
    onEditClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(contentAlignment = Alignment.BottomEnd) {
            SubcomposeAsyncImage(
                model = avatarUrl.ifBlank { null },
                contentDescription = null,
                modifier = Modifier
                    .size(130.dp)
                    .clip(CircleShape)
                    .border(4.dp, PremiumSurface, CircleShape)
                    .background(PremiumBackground),
                contentScale = ContentScale.Crop
            ) {
                when (painter.state) {
                    is AsyncImagePainter.State.Loading, is AsyncImagePainter.State.Error -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(PremiumDivider),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = PremiumGrayText.copy(alpha = 0.5f)
                            )
                        }
                    }

                    is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
                    else -> Box(Modifier.fillMaxSize().background(PremiumDivider))
                }
            }
            Surface(
                modifier = Modifier
                    .size(40.dp)
                    .offset(x = (-4).dp, y = (-4).dp)
                    .clickable { onEditClick() },
                shape = CircleShape,
                color = LightGreen,
                shadowElevation = 4.dp,
                border = BorderStroke(3.dp, PremiumSurface)
            ) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        Spacer(Modifier.height(20.dp))
        Text(name, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = PremiumDarkText)
        Spacer(Modifier.height(4.dp))
        Text(subtitle, fontSize = 15.sp, color = PremiumGrayText)
        Spacer(Modifier.height(20.dp))
        Button(
            onClick = onEditClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = PremiumDivider,
                contentColor = PremiumDarkText
            ),
            shape = RoundedCornerShape(14.dp),
            contentPadding = PaddingValues(horizontal = 28.dp, vertical = 12.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp, pressedElevation = 0.dp)
        ) {
            Text(stringResource(Res.string.edit_button), fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
        }
    }
}

@Composable
fun StatCard(value: String, label: String, modifier: Modifier) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = PremiumSurface),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = LightGreen)
            Spacer(Modifier.height(4.dp))
            Text(label.lowercase(), fontSize = 11.sp, color = PremiumGrayText, letterSpacing = 0.5.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun BadgeItem(badge: Badge) {
    val alpha = if (badge.isUnlocked) 1f else 0.4f
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(90.dp)
            .alpha(alpha)
    ) {
        Surface(
            modifier = Modifier.size(76.dp),
            shape = RoundedCornerShape(22.dp),
            color = PremiumDivider
        ) {
            SubcomposeAsyncImage(
                model = badge.iconUrl,
                contentDescription = null,
                modifier = Modifier.padding(18.dp)
            ) {
                when (painter.state) {
                    is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
                    else -> Icon(Icons.Default.EmojiEvents, null, tint = PremiumGrayText.copy(0.4f))
                }
            }
        }
        Spacer(Modifier.height(10.dp))
        Text(
            text = badge.title,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = PremiumDarkText,
            textAlign = TextAlign.Center,
            maxLines = 2,
            lineHeight = 16.sp
        )
    }
}

@Composable
fun SettingsSection(viewModel: VolunteerProfileViewModel) {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Text(
            text = stringResource(Res.string.settings_title),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = PremiumDarkText,
            modifier = Modifier.padding(vertical = 12.dp)
        )
        ElevatedCard(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = PremiumSurface),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                SettingItem(
                    Icons.Default.Notifications, stringResource(Res.string.notifications_setting),
                    iconBg = PremiumGreenLight, iconTint = LightGreen,
                    onClick = { viewModel.onIntent(VolunteerProfileAction.NotificationSettings) }
                )
                HorizontalDivider(
                    color = PremiumDivider,
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                SettingItem(
                    Icons.Default.Language, stringResource(Res.string.language_setting), stringResource(Res.string.language_russian),
                    iconBg = LightBlue.copy(alpha = 0.2f), iconTint = LightBlue,
                    onClick = { }
                )
                HorizontalDivider(
                    color = PremiumDivider,
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                SettingItem(
                    Icons.Default.Security, stringResource(Res.string.privacy_setting),
                    iconBg = SandYellow.copy(alpha = 0.2f), iconTint = SandYellow,
                    onClick = { }
                )
            }
        }
    }
}

@Composable
fun SettingItem(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    iconBg: Color,
    iconTint: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 16.dp, horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(shape = RoundedCornerShape(14.dp), color = iconBg) {
            Icon(icon, null, modifier = Modifier
                .padding(10.dp)
                .size(24.dp), tint = iconTint)
        }
        Spacer(Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = PremiumDarkText)
            if (subtitle != null) {
                Spacer(Modifier.height(2.dp))
                Text(subtitle, fontSize = 13.sp, color = PremiumGrayText)
            }
        }
        Icon(Icons.Default.ChevronRight, null, tint = PremiumGrayText.copy(alpha = 0.6f))
    }
}

@Composable
fun LogoutButton(onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        color = PremiumRedLight
    ) {
        Row(
            modifier = Modifier.padding(16.dp), 
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(Icons.Default.ExitToApp, null, tint = PremiumRed)
            Spacer(Modifier.width(12.dp))
            Text(stringResource(Res.string.logout_button), color = PremiumRed, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

@Composable
fun SectionHeader(title: String, onAllClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = PremiumDarkText)
        Text(
            stringResource(Res.string.all_button),
            color = LightGreen,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            modifier = Modifier.clickable { onAllClick() }.padding(4.dp)
        )
    }
}