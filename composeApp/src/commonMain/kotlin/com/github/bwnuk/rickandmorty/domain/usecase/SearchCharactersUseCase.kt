package com.github.bwnuk.rickandmorty.domain.usecase

import com.github.bwnuk.rickandmorty.domain.model.Character
import com.github.bwnuk.rickandmorty.domain.repository.CharacterRepository

/**
 * Use case for searching characters by name.
 */
class SearchCharactersUseCase(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(query: String, page: Int): Result<List<Character>> {
        return repository.searchCharacters(query, page)
    }
}

