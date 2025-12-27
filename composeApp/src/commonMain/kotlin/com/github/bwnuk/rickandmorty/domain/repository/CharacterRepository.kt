package com.github.bwnuk.rickandmorty.domain.repository

import com.github.bwnuk.rickandmorty.domain.model.Character

/**
 * Repository interface for character operations.
 */
interface CharacterRepository {
    /**
     * Get a paginated list of characters.
     */
    suspend fun getCharacters(page: Int): Result<List<Character>>

    /**
     * Get a single character by ID.
     */
    suspend fun getCharacterById(id: Int): Result<Character>

    /**
     * Search characters by name.
     */
    suspend fun searchCharacters(query: String, page: Int): Result<List<Character>>
}

