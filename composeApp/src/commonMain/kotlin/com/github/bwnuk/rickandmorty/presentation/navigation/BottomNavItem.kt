package com.github.bwnuk.rickandmorty.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Sealed class for bottom navigation items.
 */
sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Home : BottomNavItem(
        route = Screen.Home.route,
        title = "Home",
        icon = Icons.Default.Home
    )

    data object Favorites : BottomNavItem(
        route = Screen.Favorites.route,
        title = "Favorites",
        icon = Icons.Default.Favorite
    )

    data object Search : BottomNavItem(
        route = Screen.Search.route,
        title = "Search",
        icon = Icons.Default.Search
    )

    companion object {
        val items = listOf(Home, Favorites, Search)
    }
}

