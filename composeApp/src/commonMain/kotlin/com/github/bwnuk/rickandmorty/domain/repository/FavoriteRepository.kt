package com.github.bwnuk.rickandmorty.domain.repository

import com.github.bwnuk.rickandmorty.domain.model.Character
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for favorite character operations.
 */
interface FavoriteRepository {
    /**
     * Get all favorite characters.
     */
    fun getFavorites(): Flow<List<Character>>

    /**
     * Check if a character is marked as favorite.
     */
    fun isFavorite(characterId: Int): Flow<Boolean>

    /**
     * Add a character to favorites.
     */
    suspend fun addToFavorites(character: Character)

    /**
     * Remove a character from favorites.
     */
    suspend fun removeFromFavorites(characterId: Int)
}

