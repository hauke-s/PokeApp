package de.haukeschebitz.pokeapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DuelDto(
    val pokemon1: Int,
    val pokemon2: Int,
    val description: String? = null,
)
