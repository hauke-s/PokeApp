package de.haukeschebitz.pokeapp.presentation.detail

import de.haukeschebitz.pokeapp.domain.model.Duel

data class DuelUiState(
    val pokemon1: PokemonUiState,
    val pokemon2: PokemonUiState,
)

fun Duel.toUiState(): DuelUiState {
    return DuelUiState(
        pokemon1 = pokemon1.toUiState(),
        pokemon2 = pokemon2.toUiState(),
    )
}
