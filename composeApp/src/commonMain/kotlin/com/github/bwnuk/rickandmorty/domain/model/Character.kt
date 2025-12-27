package com.github.bwnuk.rickandmorty.domain.model

/**
 * Domain model representing a Rick and Morty character.
 */
data class Character(
    val id: Int,
    val name: String,
    val status: CharacterStatus,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Location,
    val location: Location,
    val imageUrl: String,
    val episode: List<String>,
    val isFavorite: Boolean = false
)

