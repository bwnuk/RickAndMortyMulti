package com.github.bwnuk.rickandmorty.domain.usecase

import com.github.bwnuk.rickandmorty.domain.model.Character
import com.github.bwnuk.rickandmorty.domain.repository.FavoriteRepository

/**
 * Use case for adding a character to favorites.
 */
class AddToFavoritesUseCase(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(character: Character) {
        repository.addToFavorites(character)
    }
}

