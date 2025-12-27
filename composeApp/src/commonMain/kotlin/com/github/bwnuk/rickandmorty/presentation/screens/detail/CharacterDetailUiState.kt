package com.github.bwnuk.rickandmorty.presentation.screens.detail

import com.github.bwnuk.rickandmorty.domain.model.Character

/**
 * UI state for Character Detail screen.
 */
sealed interface CharacterDetailUiState {
    data object Loading : CharacterDetailUiState
    data class Success(val character: Character) : CharacterDetailUiState
    data class Error(val message: String) : CharacterDetailUiState
}

