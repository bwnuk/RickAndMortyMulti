package com.github.bwnuk.rickandmorty.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.bwnuk.rickandmorty.domain.model.Character
import com.github.bwnuk.rickandmorty.domain.usecase.AddToFavoritesUseCase
import com.github.bwnuk.rickandmorty.domain.usecase.GetCharactersUseCase
import com.github.bwnuk.rickandmorty.domain.usecase.RemoveFromFavoritesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for Home screen with pagination support.
 */
class HomeViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var currentPage = 1

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            currentPage = 1
            getCharactersUseCase(currentPage)
                .onSuccess { characters ->
                    _uiState.value = HomeUiState.Success(
                        characters = characters,
                        canLoadMore = characters.isNotEmpty()
                    )
                }
                .onFailure { error ->
                    _uiState.value = HomeUiState.Error(
                        message = error.message ?: "Unknown error occurred"
                    )
                }
        }
    }

    fun loadMore() {
        val currentState = _uiState.value
        if (currentState is HomeUiState.Success &&
            !currentState.isLoadingMore &&
            currentState.canLoadMore) {
            viewModelScope.launch {
                _uiState.value = currentState.copy(isLoadingMore = true)
                currentPage++
                getCharactersUseCase(currentPage)
                    .onSuccess { newCharacters ->
                        _uiState.value = currentState.copy(
                            characters = currentState.characters + newCharacters,
                            isLoadingMore = false,
                            canLoadMore = newCharacters.isNotEmpty()
                        )
                    }
                    .onFailure {
                        currentPage--
                        _uiState.value = currentState.copy(isLoadingMore = false)
                    }
            }
        }
    }

    fun toggleFavorite(character: Character) {
        viewModelScope.launch {
            if (character.isFavorite) {
                removeFromFavoritesUseCase(character.id)
            } else {
                addToFavoritesUseCase(character)
            }
            // Reload to update favorite status
            loadCharacters()
        }
    }

    fun retry() {
        loadCharacters()
    }
}

