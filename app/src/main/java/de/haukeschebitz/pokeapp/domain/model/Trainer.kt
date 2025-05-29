package de.haukeschebitz.pokeapp.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Trainer(
    val id: String,
    val name: String,
)
