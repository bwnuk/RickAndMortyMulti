package com.github.bwnuk.rickandmorty.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.bwnuk.rickandmorty.domain.model.Character
import com.github.bwnuk.rickandmorty.presentation.components.CharacterCard
import com.github.bwnuk.rickandmorty.presentation.components.ErrorView
import com.github.bwnuk.rickandmorty.presentation.components.LoadingIndicator

/**
 * Stateless Home screen displaying character list with pagination.
 */
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onCharacterClick: (Int) -> Unit,
    onFavoriteClick: (Character) -> Unit,
    onLoadMore: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is HomeUiState.Loading -> LoadingIndicator(modifier = modifier)
        is HomeUiState.Success -> CharacterList(
            characters = uiState.characters,
            isLoadingMore = uiState.isLoadingMore,
            onCharacterClick = onCharacterClick,
            onFavoriteClick = onFavoriteClick,
            onLoadMore = onLoadMore,
            modifier = modifier
        )
        is HomeUiState.Error -> ErrorView(
            message = uiState.message,
            onRetry = onRetry,
            modifier = modifier
        )
    }
}

@Composable
private fun CharacterList(
    characters: List<Character>,
    isLoadingMore: Boolean,
    onCharacterClick: (Int) -> Unit,
    onFavoriteClick: (Character) -> Unit,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleIndex ->
                if (lastVisibleIndex != null && lastVisibleIndex >= characters.size - 3) {
                    onLoadMore()
                }
            }
    }

    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = characters,
            key = { it.id }
        ) { character ->
            CharacterCard(
                character = character,
                onClick = { onCharacterClick(character.id) },
                onFavoriteClick = { onFavoriteClick(character) }
            )
        }

        if (isLoadingMore) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

