package de.haukeschebitz.pokeapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PokemonDto(
    val id: Int,
    val name: String,
)
