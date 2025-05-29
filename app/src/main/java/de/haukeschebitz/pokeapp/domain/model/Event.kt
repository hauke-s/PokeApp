package de.haukeschebitz.pokeapp.domain.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.PersistentList

@Immutable
data class Event(
    val id: Int,
    val name: String,
    val dateTimestamp: Long,
    val location: String,
    val imageUrl: String,
    val duels: PersistentList<Duel>,
)
