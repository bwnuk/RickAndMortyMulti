package com.github.bwnuk.rickandmorty.data.local.database

import androidx.room.Room
import androidx.room.RoomDatabase
import platform.Foundation.NSHomeDirectory

/**
 * iOS implementation for database builder.
 */
internal actual fun getRoomDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFilePath = NSHomeDirectory() + "/rickandmorty.db"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath
    )
}

