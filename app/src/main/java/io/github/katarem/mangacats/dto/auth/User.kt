package io.github.katarem.mangacats.dto.auth

data class User(
    val username: String,
    val password: String,
    val profileImg: String? = null,
    val suscribedMangas : MutableList<Manga> = mutableListOf(),
    val recentMangas : MutableList<Manga> = mutableListOf()
)