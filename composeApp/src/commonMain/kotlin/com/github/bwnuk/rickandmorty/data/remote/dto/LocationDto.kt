package com.github.bwnuk.rickandmorty.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * DTO for location information from API.
 */
@Serializable
data class LocationDto(
    val name: String,
    val url: String
)

