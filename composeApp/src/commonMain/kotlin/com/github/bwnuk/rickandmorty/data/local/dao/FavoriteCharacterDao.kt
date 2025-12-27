package com.github.bwnuk.rickandmorty.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.bwnuk.rickandmorty.data.local.entity.FavoriteCharacterEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for favorite character operations.
 */
@Dao
interface FavoriteCharacterDao {

    @Query("SELECT * FROM favorite_characters ORDER BY name ASC")
    fun getAllFavorites(): Flow<List<FavoriteCharacterEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_characters WHERE id = :characterId)")
    fun isFavorite(characterId: Int): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: FavoriteCharacterEntity)

    @Query("DELETE FROM favorite_characters WHERE id = :characterId")
    suspend fun delete(characterId: Int)
}

