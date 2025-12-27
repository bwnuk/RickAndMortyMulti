package com.github.bwnuk.rickandmorty.presentation.screens.home

import com.github.bwnuk.rickandmorty.domain.model.Character

/**
 * UI state for Home screen.
 */
sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(
        val characters: List<Character>,
        val isLoadingMore: Boolean = false,
        val canLoadMore: Boolean = true
    ) : HomeUiState
    data class Error(val message: String) : HomeUiState
}

