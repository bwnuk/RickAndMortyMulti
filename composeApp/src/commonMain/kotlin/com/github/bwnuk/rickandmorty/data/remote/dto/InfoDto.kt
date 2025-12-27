package com.github.bwnuk.rickandmorty.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * DTO for API response pagination info.
 */
@Serializable
data class InfoDto(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)

