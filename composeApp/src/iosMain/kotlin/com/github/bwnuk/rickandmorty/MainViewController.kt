package com.github.bwnuk.rickandmorty

import androidx.compose.ui.window.ComposeUIViewController
import com.github.bwnuk.rickandmorty.di.appModules
import org.koin.core.context.startKoin

/**
 * iOS entry point with Koin initialization.
 */
fun MainViewController() = ComposeUIViewController {
    startKoin {
        modules(appModules)
    }
    App()
}
