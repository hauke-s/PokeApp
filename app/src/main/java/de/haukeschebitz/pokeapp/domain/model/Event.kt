package de.haukeschebitz.pokeapp.domain.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class Event(
    val id: Int,
    val name: String,
    val dateTimestamp: Long,
    val location: String,
    val imageUrl: String,
    val duels: PersistentList<Duel>,
    val isFeatured: Boolean,
)

val defaultEvent: Event = Event(
    id = 0,
    name = "Placeholder Event",
    dateTimestamp = System.currentTimeMillis(),
    location = "Somewhere",
    imageUrl = "",
    duels = persistentListOf(),
    isFeatured = false,
)