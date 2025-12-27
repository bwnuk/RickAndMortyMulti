package com.github.bwnuk.rickandmorty.presentation.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.bwnuk.rickandmorty.presentation.components.CharacterCard
import com.github.bwnuk.rickandmorty.presentation.components.EmptyView
import com.github.bwnuk.rickandmorty.presentation.components.ErrorView
import com.github.bwnuk.rickandmorty.presentation.components.LoadingIndicator

/**
 * Stateless Search screen with search bar and results.
 */
@Composable
fun SearchScreen(
    uiState: SearchUiState,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onCharacterClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        SearchBar(
            query = searchQuery,
            onQueryChange = onSearchQueryChange,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        when (uiState) {
            is SearchUiState.Idle -> EmptyView(
                message = "Search for your favorite Rick and Morty characters",
                modifier = Modifier.fillMaxSize()
            )
            is SearchUiState.Loading -> LoadingIndicator(modifier = Modifier.fillMaxSize())
            is SearchUiState.Success -> SearchResults(
                results = uiState.results,
                onCharacterClick = onCharacterClick,
                modifier = Modifier.fillMaxSize()
            )
            is SearchUiState.Empty -> EmptyView(
                message = "No characters found for \"$searchQuery\"",
                modifier = Modifier.fillMaxSize()
            )
            is SearchUiState.Error -> ErrorView(
                message = uiState.message,
                onRetry = { },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier,
        placeholder = { Text("Search characters...") },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Search")
        },
        singleLine = true
    )
}

@Composable
private fun SearchResults(
    results: List<com.github.bwnuk.rickandmorty.domain.model.Character>,
    onCharacterClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = results,
            key = { it.id }
        ) { character ->
            CharacterCard(
                character = character,
                onClick = { onCharacterClick(character.id) },
                onFavoriteClick = { }
            )
        }
    }
}

