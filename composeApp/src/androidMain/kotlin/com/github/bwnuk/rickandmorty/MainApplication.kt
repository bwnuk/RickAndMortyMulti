package com.github.bwnuk.rickandmorty

import android.app.Application
import com.github.bwnuk.rickandmorty.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Application class for Koin initialization.
 */
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(appModules)
        }
    }
}

