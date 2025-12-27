package com.github.bwnuk.rickandmorty.presentation.screens.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.bwnuk.rickandmorty.presentation.components.CharacterCard
import com.github.bwnuk.rickandmorty.presentation.components.EmptyView
import com.github.bwnuk.rickandmorty.presentation.components.LoadingIndicator

/**
 * Stateless Favorites screen displaying saved favorite characters.
 */
@Composable
fun FavoritesScreen(
    uiState: FavoritesUiState,
    onCharacterClick: (Int) -> Unit,
    onRemoveFavorite: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is FavoritesUiState.Loading -> LoadingIndicator(modifier = modifier)
        is FavoritesUiState.Success -> FavoritesList(
            favorites = uiState.favorites,
            onCharacterClick = onCharacterClick,
            onRemoveFavorite = onRemoveFavorite,
            modifier = modifier
        )
        is FavoritesUiState.Empty -> EmptyView(
            message = "No favorite characters yet.\nAdd some from the home screen!",
            modifier = modifier
        )
    }
}

@Composable
private fun FavoritesList(
    favorites: List<com.github.bwnuk.rickandmorty.domain.model.Character>,
    onCharacterClick: (Int) -> Unit,
    onRemoveFavorite: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = favorites,
            key = { it.id }
        ) { character ->
            CharacterCard(
                character = character,
                onClick = { onCharacterClick(character.id) },
                onFavoriteClick = { onRemoveFavorite(character.id) }
            )
        }
    }
}

