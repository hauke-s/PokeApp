package de.haukeschebitz.pokeapp.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String? = null,
)
