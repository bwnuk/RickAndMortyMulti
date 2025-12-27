package com.github.bwnuk.rickandmorty.domain.model

/**
 * Represents the status of a character.
 */
enum class CharacterStatus(val displayName: String) {
    ALIVE("Alive"),
    DEAD("Dead"),
    UNKNOWN("Unknown");

    companion object {
        fun fromString(value: String): CharacterStatus {
            return when (value.lowercase()) {
                "alive" -> ALIVE
                "dead" -> DEAD
                else -> UNKNOWN
            }
        }
    }
}

