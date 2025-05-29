package de.haukeschebitz.pokeapp.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Duel(
    val pokemon: Pokemon,
    val pokemon2: Pokemon,
)
