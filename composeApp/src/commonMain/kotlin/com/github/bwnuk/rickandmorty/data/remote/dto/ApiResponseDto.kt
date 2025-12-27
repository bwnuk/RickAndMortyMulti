package com.github.bwnuk.rickandmorty.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * Generic DTO for paginated API responses.
 */
@Serializable
data class ApiResponseDto<T>(
    val info: InfoDto,
    val results: List<T>
)

