package com.github.bwnuk.rickandmorty.data.remote.api

import com.github.bwnuk.rickandmorty.data.remote.dto.ApiResponseDto
import com.github.bwnuk.rickandmorty.data.remote.dto.CharacterDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

/**
 * API service for Rick and Morty API.
 */
class RickAndMortyApi(private val client: HttpClient) {

    private companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api"
    }

    /**
     * Get a paginated list of characters.
     */
    suspend fun getCharacters(page: Int): ApiResponseDto<CharacterDto> {
        return client.get("$BASE_URL/character") {
            parameter("page", page)
        }.body()
    }

    /**
     * Get a single character by ID.
     */
    suspend fun getCharacterById(id: Int): CharacterDto {
        return client.get("$BASE_URL/character/$id").body()
    }

    /**
     * Search characters by name.
     */
    suspend fun searchCharacters(name: String, page: Int): ApiResponseDto<CharacterDto> {
        return client.get("$BASE_URL/character") {
            parameter("name", name)
            parameter("page", page)
        }.body()
    }
}

