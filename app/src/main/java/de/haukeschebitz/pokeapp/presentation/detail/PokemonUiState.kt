package de.haukeschebitz.pokeapp.presentation.detail

import de.haukeschebitz.pokeapp.domain.model.Pokemon

data class PokemonUiState(
    val id: Int,
    val name: String,
    val imageUrl: String? = null,
)

fun Pokemon.toUiState(): PokemonUiState {
    return PokemonUiState(
        id = id,
        name = name,
        imageUrl = imageUrl,
    )
}
