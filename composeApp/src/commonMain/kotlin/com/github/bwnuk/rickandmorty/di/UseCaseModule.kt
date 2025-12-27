package com.github.bwnuk.rickandmorty.di

import com.github.bwnuk.rickandmorty.domain.usecase.AddToFavoritesUseCase
import com.github.bwnuk.rickandmorty.domain.usecase.GetCharacterByIdUseCase
import com.github.bwnuk.rickandmorty.domain.usecase.GetCharactersUseCase
import com.github.bwnuk.rickandmorty.domain.usecase.GetFavoritesUseCase
import com.github.bwnuk.rickandmorty.domain.usecase.IsFavoriteUseCase
import com.github.bwnuk.rickandmorty.domain.usecase.RemoveFromFavoritesUseCase
import com.github.bwnuk.rickandmorty.domain.usecase.SearchCharactersUseCase
import org.koin.dsl.module

/**
 * Koin module for use case dependencies.
 */
val useCaseModule = module {
    factory { GetCharactersUseCase(get()) }
    factory { GetCharacterByIdUseCase(get()) }
    factory { SearchCharactersUseCase(get()) }
    factory { GetFavoritesUseCase(get()) }
    factory { AddToFavoritesUseCase(get()) }
    factory { RemoveFromFavoritesUseCase(get()) }
    factory { IsFavoriteUseCase(get()) }
}

