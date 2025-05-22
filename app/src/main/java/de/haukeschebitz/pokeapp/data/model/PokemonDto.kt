package de.haukeschebitz.pokeapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDto(
    val id: Int,
    val name: String,
    val sprites: SpritesDto,
)

@Serializable
data class SpritesDto(
    @SerialName("front_default") val frontDefault: String?,
)
