package de.haukeschebitz.pokeapp.domain.mapper

import de.haukeschebitz.pokeapp.data.model.EventDto
import de.haukeschebitz.pokeapp.domain.model.Event
import kotlinx.collections.immutable.persistentListOf

fun EventDto.toDomain(): Event {
    return Event(
        id = id,
        name = title,
        dateTimestamp = dateTimestamp,
        location = location,
        imageUrl = imageUrl,
        duels = persistentListOf(),
        isFeatured = isFeatured,
    )
}
