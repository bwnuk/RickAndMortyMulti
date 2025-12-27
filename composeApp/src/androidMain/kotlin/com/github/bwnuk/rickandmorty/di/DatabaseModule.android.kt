package com.github.bwnuk.rickandmorty.di

import com.github.bwnuk.rickandmorty.data.local.database.AppDatabase
import com.github.bwnuk.rickandmorty.data.local.database.getRoomDatabaseBuilder
import com.github.bwnuk.rickandmorty.data.local.database.initializeDatabaseContext
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Android implementation of database module.
 */
actual val databaseModule = module {
    single<AppDatabase> {
        initializeDatabaseContext(androidContext())
        getRoomDatabaseBuilder().build()
    }
    single {
        get<AppDatabase>().favoriteCharacterDao()
    }
}

