package org.example.volunteer.di

import org.example.volunteer.data.repository.NotificationRepImpl
import org.example.volunteer.domain.repository.NotificationRepository
import org.koin.dsl.module

val notificationRepModule = module {
    single<NotificationRepository> { NotificationRepImpl(get()) }
}