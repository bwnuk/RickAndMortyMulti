package com.github.bwnuk.rickandmorty.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.bwnuk.rickandmorty.domain.usecase.SearchCharactersUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

/**
 * ViewModel for Search screen with debounced search.
 */
@OptIn(FlowPreview::class)
class SearchViewModel(
    private val searchCharactersUseCase: SearchCharactersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(300)
                .distinctUntilChanged()
                .filter { it.isNotBlank() }
                .collect { query ->
                    performSearch(query)
                }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        if (query.isBlank()) {
            _uiState.value = SearchUiState.Idle
        } else {
            _uiState.value = SearchUiState.Loading
        }
    }

    private suspend fun performSearch(query: String) {
        searchCharactersUseCase(query, page = 1)
            .onSuccess { results ->
                _uiState.value = if (results.isEmpty()) {
                    SearchUiState.Empty
                } else {
                    SearchUiState.Success(results)
                }
            }
            .onFailure { error ->
                _uiState.value = SearchUiState.Error(
                    message = error.message ?: "Search failed"
                )
            }
    }
}

