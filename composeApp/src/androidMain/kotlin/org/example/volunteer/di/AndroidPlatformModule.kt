package org.example.volunteer.di

import org.example.volunteer.BuildConfig
import org.example.volunteer.core.network.HttpVerboseLogging
import org.koin.dsl.module

val androidPlatformModule = module {
    single<HttpVerboseLogging> { HttpVerboseLogging { BuildConfig.DEBUG } }
}
