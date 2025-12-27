package com.github.bwnuk.rickandmorty

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform