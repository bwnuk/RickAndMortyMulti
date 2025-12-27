package com.github.bwnuk.rickandmorty.domain.usecase

import com.github.bwnuk.rickandmorty.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case for checking if a character is marked as favorite.
 */
class IsFavoriteUseCase(
    private val repository: FavoriteRepository
) {
    operator fun invoke(characterId: Int): Flow<Boolean> {
        return repository.isFavorite(characterId)
    }
}

