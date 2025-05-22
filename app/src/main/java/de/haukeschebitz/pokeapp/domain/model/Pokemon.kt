package de.haukeschebitz.pokeapp.domain.model

data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String? = null,
)
