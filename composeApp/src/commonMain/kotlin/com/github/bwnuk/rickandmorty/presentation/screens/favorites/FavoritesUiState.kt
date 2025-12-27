package com.github.bwnuk.rickandmorty.presentation.screens.favorites

import com.github.bwnuk.rickandmorty.domain.model.Character

/**
 * UI state for Favorites screen.
 */
sealed interface FavoritesUiState {
    data object Loading : FavoritesUiState
    data class Success(val favorites: List<Character>) : FavoritesUiState
    data object Empty : FavoritesUiState
}

