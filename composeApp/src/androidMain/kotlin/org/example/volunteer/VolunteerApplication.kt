package org.example.volunteer

import android.app.Application
import org.example.volunteer.di.androidPlatformModule
import org.example.volunteer.di.appModule
import org.example.volunteer.di.dataStoreModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class VolunteerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@VolunteerApplication)
            modules(appModule, dataStoreModule, androidPlatformModule)
        }
    }
}