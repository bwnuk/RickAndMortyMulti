package com.github.bwnuk.rickandmorty.data.mapper

import com.github.bwnuk.rickandmorty.data.remote.dto.CharacterDto
import com.github.bwnuk.rickandmorty.data.remote.dto.LocationDto
import com.github.bwnuk.rickandmorty.domain.model.Character
import com.github.bwnuk.rickandmorty.domain.model.CharacterStatus
import com.github.bwnuk.rickandmorty.domain.model.Location

/**
 * Mapper for converting between DTOs and domain models.
 */
internal class CharacterMapper {

    fun toDomain(dto: CharacterDto, isFavorite: Boolean = false): Character {
        return Character(
            id = dto.id,
            name = dto.name,
            status = CharacterStatus.fromString(dto.status),
            species = dto.species,
            type = dto.type,
            gender = dto.gender,
            origin = toDomain(dto.origin),
            location = toDomain(dto.location),
            imageUrl = dto.image,
            episode = dto.episode,
            isFavorite = isFavorite
        )
    }

    private fun toDomain(dto: LocationDto): Location {
        return Location(
            name = dto.name,
            url = dto.url
        )
    }
}

