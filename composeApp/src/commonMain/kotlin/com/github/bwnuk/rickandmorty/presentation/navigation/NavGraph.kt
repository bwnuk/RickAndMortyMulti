package com.github.bwnuk.rickandmorty.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.github.bwnuk.rickandmorty.presentation.screens.detail.CharacterDetailScreen
import com.github.bwnuk.rickandmorty.presentation.screens.detail.CharacterDetailViewModel
import com.github.bwnuk.rickandmorty.presentation.screens.favorites.FavoritesScreen
import com.github.bwnuk.rickandmorty.presentation.screens.favorites.FavoritesViewModel
import com.github.bwnuk.rickandmorty.presentation.screens.home.HomeScreen
import com.github.bwnuk.rickandmorty.presentation.screens.home.HomeViewModel
import com.github.bwnuk.rickandmorty.presentation.screens.search.SearchScreen
import com.github.bwnuk.rickandmorty.presentation.screens.search.SearchViewModel
import org.koin.compose.viewmodel.koinViewModel

/**
 * Navigation graph connecting ViewModels to stateless screens.
 * This is where ViewModels are instantiated and connected to screens.
 */
@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            val viewModel: HomeViewModel = koinViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            HomeScreen(
                uiState = uiState,
                onCharacterClick = { characterId ->
                    navController.navigate(Screen.CharacterDetail.createRoute(characterId))
                },
                onFavoriteClick = viewModel::toggleFavorite,
                onLoadMore = viewModel::loadMore,
                onRetry = viewModel::retry
            )
        }

        composable(Screen.Favorites.route) {
            val viewModel: FavoritesViewModel = koinViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            FavoritesScreen(
                uiState = uiState,
                onCharacterClick = { characterId ->
                    navController.navigate(Screen.CharacterDetail.createRoute(characterId))
                },
                onRemoveFavorite = viewModel::removeFavorite
            )
        }

        composable(Screen.Search.route) {
            val viewModel: SearchViewModel = koinViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

            SearchScreen(
                uiState = uiState,
                searchQuery = searchQuery,
                onSearchQueryChange = viewModel::onSearchQueryChange,
                onCharacterClick = { characterId ->
                    navController.navigate(Screen.CharacterDetail.createRoute(characterId))
                }
            )
        }

        composable(
            route = Screen.CharacterDetail.route,
            arguments = listOf(navArgument("characterId") { type = NavType.IntType })
        ) { backStackEntry ->
            val characterId = backStackEntry.arguments?.getInt("characterId") ?: return@composable
            val viewModel: CharacterDetailViewModel = koinViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(characterId) {
                viewModel.loadCharacter(characterId)
            }

            CharacterDetailScreen(
                uiState = uiState,
                onBackClick = { navController.popBackStack() },
                onFavoriteClick = viewModel::toggleFavorite,
                onRetry = { viewModel.loadCharacter(characterId) }
            )
        }
    }
}

