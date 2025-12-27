package com.github.bwnuk.rickandmorty.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.bwnuk.rickandmorty.data.local.dao.FavoriteCharacterDao
import com.github.bwnuk.rickandmorty.data.local.entity.FavoriteCharacterEntity

/**
 * Room database for the application.
 */
@Database(
    entities = [FavoriteCharacterEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteCharacterDao(): FavoriteCharacterDao
}

