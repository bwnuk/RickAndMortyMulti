package com.github.bwnuk.rickandmorty.domain.usecase

import com.github.bwnuk.rickandmorty.domain.repository.FavoriteRepository

/**
 * Use case for removing a character from favorites.
 */
class RemoveFromFavoritesUseCase(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(characterId: Int) {
        repository.removeFromFavorites(characterId)
    }
}

