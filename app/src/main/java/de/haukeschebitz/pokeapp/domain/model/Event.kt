package de.haukeschebitz.pokeapp.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Event(
    val id: Int,
    val name: String,
    val dateTimestamp: Long,
    val location: String,
    val imageUrl: String,
    val duels: List<Duel>,
    val isFeatured: Boolean,
)
