package com.github.bwnuk.rickandmorty.data.repository

import com.github.bwnuk.rickandmorty.data.local.dao.FavoriteCharacterDao
import com.github.bwnuk.rickandmorty.data.mapper.CharacterMapper
import com.github.bwnuk.rickandmorty.data.remote.api.RickAndMortyApi
import com.github.bwnuk.rickandmorty.domain.model.Character
import com.github.bwnuk.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.firstOrNull

/**
 * Implementation of CharacterRepository.
 */
internal class CharacterRepositoryImpl(
    private val api: RickAndMortyApi,
    private val favoriteDao: FavoriteCharacterDao,
    private val mapper: CharacterMapper
) : CharacterRepository {

    override suspend fun getCharacters(page: Int): Result<List<Character>> {
        return try {
            val response = api.getCharacters(page)
            val characters = response.results.map { dto ->
                val isFavorite = favoriteDao.isFavorite(dto.id).firstOrNull() ?: false
                mapper.toDomain(dto, isFavorite)
            }
            Result.success(characters)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCharacterById(id: Int): Result<Character> {
        return try {
            val dto = api.getCharacterById(id)
            val isFavorite = favoriteDao.isFavorite(dto.id).firstOrNull() ?: false
            val character = mapper.toDomain(dto, isFavorite)
            Result.success(character)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchCharacters(query: String, page: Int): Result<List<Character>> {
        return try {
            val response = api.searchCharacters(query, page)
            val characters = response.results.map { dto ->
                val isFavorite = favoriteDao.isFavorite(dto.id).firstOrNull() ?: false
                mapper.toDomain(dto, isFavorite)
            }
            Result.success(characters)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

