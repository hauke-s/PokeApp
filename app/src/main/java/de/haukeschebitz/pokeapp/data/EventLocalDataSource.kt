package de.haukeschebitz.pokeapp.data

import de.haukeschebitz.pokeapp.domain.model.Duel
import de.haukeschebitz.pokeapp.domain.model.Event
import de.haukeschebitz.pokeapp.domain.model.Pokemon
import kotlinx.collections.immutable.persistentListOf

interface EventLocalDataSource {
    val eventList: List<Event>
}

class MockEventLocalDataSourceImpl : EventLocalDataSource {
    override val eventList: List<Event> = listOf(
        Event(
            id = 0,
            name = "Event 0",
            dateTimestamp = System.currentTimeMillis(),
            location = "Hamburg",
            imageUrl = "",
            duels = persistentListOf(
                Duel(Pokemon(0, "Pikachu"), Pokemon(1, "Pika")),
            ),
        ),
        Event(
            id = 1,
            name = "Event 1",
            dateTimestamp = System.currentTimeMillis(),
            location = "Köln",
            imageUrl = "",
            duels = persistentListOf(
                Duel(Pokemon(0, "Relaxo"), Pokemon(1, "Pikachu")),
            ),
        )
    )
}