package com.github.bwnuk.rickandmorty.di

import org.koin.core.module.Module

/**
 * Platform-specific Koin module for database dependencies.
 * Actual implementations in androidMain and iosMain.
 */
expect val databaseModule: Module

