package com.github.bwnuk.rickandmorty.di

/**
 * Aggregates all Koin modules.
 */
val appModules = listOf(
    networkModule,
    databaseModule,
    repositoryModule,
    useCaseModule,
    viewModelModule
)

