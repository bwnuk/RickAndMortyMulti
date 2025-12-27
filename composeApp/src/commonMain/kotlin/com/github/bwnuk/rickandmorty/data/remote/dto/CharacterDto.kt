package com.github.bwnuk.rickandmorty.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * DTO for character data from API.
 */
@Serializable
data class CharacterDto(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: LocationDto,
    val location: LocationDto,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)

