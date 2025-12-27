package com.github.bwnuk.rickandmorty.domain.usecase

import com.github.bwnuk.rickandmorty.domain.model.Character
import com.github.bwnuk.rickandmorty.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case for getting all favorite characters.
 */
class GetFavoritesUseCase(
    private val repository: FavoriteRepository
) {
    operator fun invoke(): Flow<List<Character>> {
        return repository.getFavorites()
    }
}

