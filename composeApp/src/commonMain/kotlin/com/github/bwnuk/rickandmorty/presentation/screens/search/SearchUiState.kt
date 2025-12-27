package com.github.bwnuk.rickandmorty.presentation.screens.search

import com.github.bwnuk.rickandmorty.domain.model.Character

/**
 * UI state for Search screen.
 */
sealed interface SearchUiState {
    data object Idle : SearchUiState
    data object Loading : SearchUiState
    data class Success(val results: List<Character>) : SearchUiState
    data object Empty : SearchUiState
    data class Error(val message: String) : SearchUiState
}

