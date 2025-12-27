package com.github.bwnuk.rickandmorty.di

import com.github.bwnuk.rickandmorty.data.local.database.AppDatabase
import com.github.bwnuk.rickandmorty.data.local.database.getRoomDatabaseBuilder
import org.koin.dsl.module

/**
 * iOS implementation of database module.
 */
actual val databaseModule = module {
    single<AppDatabase> {
        getRoomDatabaseBuilder().build()
    }
    single {
        get<AppDatabase>().favoriteCharacterDao()
    }
}

