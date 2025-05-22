package de.haukeschebitz.pokeapp.domain.model

data class Event(
    val id: Int,
    val name: String,
    val dateTimestamp: Long,
    val location: String,
    val imageUrl: String,
    val duels: List<Duel>,
)
