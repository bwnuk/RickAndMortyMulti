package com.github.bwnuk.rickandmorty.data.local.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

// This will be set by the DI module
private lateinit var applicationContext: Context

/**
 * Initialize the database builder with Android context.
 * Called from Koin DI module.
 */
fun initializeDatabaseContext(context: Context) {
    applicationContext = context.applicationContext
}

/**
 * Android implementation for database builder.
 */
internal actual fun getRoomDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFile = applicationContext.getDatabasePath("rickandmorty.db")
    return Room.databaseBuilder<AppDatabase>(
        context = applicationContext,
        name = dbFile.absolutePath
    )
}

