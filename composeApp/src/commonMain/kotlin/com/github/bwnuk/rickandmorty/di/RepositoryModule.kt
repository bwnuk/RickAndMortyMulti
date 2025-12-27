package com.github.bwnuk.rickandmorty.di

import com.github.bwnuk.rickandmorty.data.mapper.CharacterMapper
import com.github.bwnuk.rickandmorty.data.remote.api.RickAndMortyApi
import com.github.bwnuk.rickandmorty.data.repository.CharacterRepositoryImpl
import com.github.bwnuk.rickandmorty.data.repository.FavoriteRepositoryImpl
import com.github.bwnuk.rickandmorty.domain.repository.CharacterRepository
import com.github.bwnuk.rickandmorty.domain.repository.FavoriteRepository
import org.koin.dsl.module

/**
 * Koin module for repository dependencies.
 */
val repositoryModule = module {
    single { CharacterMapper() }
    single { RickAndMortyApi(get()) }
    single<CharacterRepository> { CharacterRepositoryImpl(get(), get(), get()) }
    single<FavoriteRepository> { FavoriteRepositoryImpl(get(), get()) }
}

