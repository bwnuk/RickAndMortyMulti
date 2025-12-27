package com.github.bwnuk.rickandmorty.di

import com.github.bwnuk.rickandmorty.presentation.screens.detail.CharacterDetailViewModel
import com.github.bwnuk.rickandmorty.presentation.screens.favorites.FavoritesViewModel
import com.github.bwnuk.rickandmorty.presentation.screens.home.HomeViewModel
import com.github.bwnuk.rickandmorty.presentation.screens.search.SearchViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Koin module for ViewModel dependencies.
 */
val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::FavoritesViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::CharacterDetailViewModel)
}

