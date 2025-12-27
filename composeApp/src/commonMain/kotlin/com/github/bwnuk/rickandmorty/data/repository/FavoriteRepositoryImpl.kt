package com.github.bwnuk.rickandmorty.data.repository

import com.github.bwnuk.rickandmorty.data.local.dao.FavoriteCharacterDao
import com.github.bwnuk.rickandmorty.data.local.entity.FavoriteCharacterEntity
import com.github.bwnuk.rickandmorty.data.mapper.CharacterMapper
import com.github.bwnuk.rickandmorty.domain.model.Character
import com.github.bwnuk.rickandmorty.domain.model.CharacterStatus
import com.github.bwnuk.rickandmorty.domain.model.Location
import com.github.bwnuk.rickandmorty.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of FavoriteRepository.
 */
internal class FavoriteRepositoryImpl(
    private val dao: FavoriteCharacterDao,
    private val mapper: CharacterMapper
) : FavoriteRepository {

    override fun getFavorites(): Flow<List<Character>> {
        return dao.getAllFavorites().map { entities ->
            entities.map { entity -> toDomain(entity) }
        }
    }

    override fun isFavorite(characterId: Int): Flow<Boolean> {
        return dao.isFavorite(characterId)
    }

    override suspend fun addToFavorites(character: Character) {
        dao.insert(toEntity(character))
    }

    override suspend fun removeFromFavorites(characterId: Int) {
        dao.delete(characterId)
    }

    private fun toEntity(character: Character): FavoriteCharacterEntity {
        return FavoriteCharacterEntity(
            id = character.id,
            name = character.name,
            status = character.status.name,
            species = character.species,
            type = character.type,
            gender = character.gender,
            originName = character.origin.name,
            originUrl = character.origin.url,
            locationName = character.location.name,
            locationUrl = character.location.url,
            imageUrl = character.imageUrl,
            episodes = character.episode.joinToString(",")
        )
    }

    private fun toDomain(entity: FavoriteCharacterEntity): Character {
        return Character(
            id = entity.id,
            name = entity.name,
            status = CharacterStatus.fromString(entity.status),
            species = entity.species,
            type = entity.type,
            gender = entity.gender,
            origin = Location(entity.originName, entity.originUrl),
            location = Location(entity.locationName, entity.locationUrl),
            imageUrl = entity.imageUrl,
            episode = entity.episodes.split(","),
            isFavorite = true
        )
    }
}

