package com.github.bwnuk.rickandmorty.data.local.database

import androidx.room.RoomDatabase

/**
 * Platform-specific function for creating database builder.
 * Implemented in androidMain and iosMain through Koin DI.
 */
internal expect fun getRoomDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>

