package com.github.bwnuk.rickandmorty.presentation.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.bwnuk.rickandmorty.domain.model.Character
import com.github.bwnuk.rickandmorty.domain.usecase.AddToFavoritesUseCase
import com.github.bwnuk.rickandmorty.domain.usecase.GetCharacterByIdUseCase
import com.github.bwnuk.rickandmorty.domain.usecase.RemoveFromFavoritesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for Character Detail screen.
 */
class CharacterDetailViewModel(
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CharacterDetailUiState>(CharacterDetailUiState.Loading)
    val uiState: StateFlow<CharacterDetailUiState> = _uiState.asStateFlow()

    fun loadCharacter(characterId: Int) {
        viewModelScope.launch {
            _uiState.value = CharacterDetailUiState.Loading
            getCharacterByIdUseCase(characterId)
                .onSuccess { character ->
                    _uiState.value = CharacterDetailUiState.Success(character)
                }
                .onFailure { error ->
                    _uiState.value = CharacterDetailUiState.Error(
                        message = error.message ?: "Failed to load character"
                    )
                }
        }
    }

    fun toggleFavorite() {
        val currentState = _uiState.value
        if (currentState is CharacterDetailUiState.Success) {
            viewModelScope.launch {
                val character = currentState.character
                if (character.isFavorite) {
                    removeFromFavoritesUseCase(character.id)
                } else {
                    addToFavoritesUseCase(character)
                }
                // Reload to update favorite status
                loadCharacter(character.id)
            }
        }
    }
}

