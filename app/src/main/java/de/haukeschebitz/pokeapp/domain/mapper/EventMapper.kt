package de.haukeschebitz.pokeapp.domain.mapper

import de.haukeschebitz.pokeapp.data.model.EventDto
import de.haukeschebitz.pokeapp.domain.model.Duel
import de.haukeschebitz.pokeapp.domain.model.Event
import de.haukeschebitz.pokeapp.domain.model.Pokemon

fun EventDto.toDomain(pokemonMap: Map<Int, Pokemon>): Event {
    return Event(
        id = id,
        name = title,
        dateTimestamp = dateTimestamp,
        location = location,
        imageUrl = imageUrl,
        duels = duels.map { duel ->
            Duel(
                pokemon1 = pokemonMap.getValue(duel.pokemon1),
                pokemon2 = pokemonMap.getValue(duel.pokemon2),
            )
        },
        isFeatured = isFeatured,
    )
}
