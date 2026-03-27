package org.example.volunteer.di

import kotlinx.serialization.json.Json
import org.example.volunteer.AppViewModel
import org.example.volunteer.core.network.createHttpClient
import org.example.volunteer.data.local.AppDatabase
import org.example.volunteer.data.remote.api.AIApi
import org.example.volunteer.data.remote.api.ApplicationApi
import org.example.volunteer.data.remote.api.AuthApi
import org.example.volunteer.data.remote.api.ChatApi
import org.example.volunteer.data.remote.api.EventApi
import org.example.volunteer.data.remote.api.ProfileApi
import org.example.volunteer.data.repository.ApplicationRepositoryImpl
import org.example.volunteer.data.repository.ChatRepositoryImpl
import org.example.volunteer.data.repository.EventRepositoryImpl
import org.example.volunteer.data.repository.SettingsRepositoryImpl
import org.example.volunteer.data.repository.UserRepositoryImpl
import org.example.volunteer.domain.repository.ApplicationRepository
import org.example.volunteer.domain.repository.ChatRepository
import org.example.volunteer.domain.repository.EventRepository
import org.example.volunteer.domain.repository.SettingsRepository
import org.example.volunteer.domain.repository.UserRepository
import org.example.volunteer.domain.usecase.AcceptApplicantUseCase
import org.example.volunteer.domain.usecase.ApplyForEventUseCase
import org.example.volunteer.domain.usecase.CancelApplicationUseCase
import org.example.volunteer.domain.usecase.CreateEventUseCase
import org.example.volunteer.domain.usecase.LoginUseCase
import org.example.volunteer.domain.usecase.LogoutUseCase
import org.example.volunteer.domain.usecase.RegisterUseCase
import org.example.volunteer.domain.usecase.RejectApplicantUseCase
import org.example.volunteer.domain.usecase.SendMessageUseCase
import org.example.volunteer.presentation.screens.aicreate.AICreateViewModel
import org.example.volunteer.presentation.screens.chatlist.ChatListViewModel
import org.example.volunteer.presentation.screens.chatroom.ChatRoomViewModel
import org.example.volunteer.presentation.screens.createevent.CreateEventViewModel
import org.example.volunteer.presentation.screens.eventdetails.EventDetailsViewModel
import org.example.volunteer.presentation.screens.login.LoginViewModel
import org.example.volunteer.presentation.screens.mainevent.MainEventViewModel
import org.example.volunteer.presentation.screens.manageevents.ManageEventViewModel
import org.example.volunteer.presentation.screens.myevents.MyEventsViewModel
import org.example.volunteer.presentation.screens.orgmyevents.OrgMyEventsViewModel
import org.example.volunteer.presentation.screens.orgprofile.OrgProfileViewModel
import org.example.volunteer.presentation.screens.registration.RegistrationViewModel
import org.example.volunteer.presentation.screens.volunteerprofile.VolunteerProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory { AcceptApplicantUseCase(get()) }
    factory { ApplyForEventUseCase(get(), get(), get()) }
    factory { CancelApplicationUseCase(get(), get()) }
    factory { CreateEventUseCase(get()) }
    factory { LoginUseCase(get(), get()) }
    factory { LogoutUseCase(get()) }
    factory { RegisterUseCase(get(), get()) }
    factory { RejectApplicantUseCase(get()) }
    factory { SendMessageUseCase(get()) }

    viewModel { AICreateViewModel(get()) }
    viewModel { ChatListViewModel(get()) }
    viewModel { ChatRoomViewModel(get(), get()) }
    viewModel { CreateEventViewModel(get(), get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { RegistrationViewModel(get()) }
    viewModel { MainEventViewModel(get()) }
    viewModel { EventDetailsViewModel(get(), get(), get()) }
    viewModel { MyEventsViewModel(get(), get()) }
    viewModel { OrgMyEventsViewModel(get()) }
    viewModel { ManageEventViewModel(get(), get(), get(), get()) }
    viewModel { VolunteerProfileViewModel(get(), get(),get()) }
    viewModel { OrgProfileViewModel(get(), get()) }
    viewModel { AppViewModel(get()) }

    single { createHttpClient(get()) }
    single { AuthApi(get()) }
    single { AuthApi(get()) }
    single { EventApi(get()) }
    single { ApplicationApi(get()) }
    single { ChatApi(get()) }
    single { ProfileApi(get()) }
    single { AIApi(get()) }
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get(), get(), get()) }
    single<EventRepository> { EventRepositoryImpl(get(), get()) }
    single<ApplicationRepository> { ApplicationRepositoryImpl(get()) }
    single { Json { ignoreUnknownKeys = true; isLenient = true } }
    single<ChatRepository> { ChatRepositoryImpl(get(), get(), get(), get()) }
    single { get<AppDatabase>().eventDao()}
}