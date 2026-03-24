package org.example.volunteer.di

import android.content.Context
import org.example.volunteer.core.datastore.createDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {
    single { createDataStore(androidContext()) }
}