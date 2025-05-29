package de.haukeschebitz.pokeapp.domain

import de.haukeschebitz.pokeapp.data.EventLocalDataSource
import de.haukeschebitz.pokeapp.domain.model.Event

interface EventRepository {
    suspend fun getEvent(eventId: Int): Event?
    suspend fun getEventList(): List<Event>
    suspend fun getFeaturedEvent(): Event?
}

class EventRepositoryImpl(
    private val localDataSource: EventLocalDataSource,
) : EventRepository {

    override suspend fun getEvent(eventId: Int): Event? {
        return localDataSource.eventList.firstOrNull { it.id == eventId }
    }

    override suspend fun getEventList(): List<Event> {
        return localDataSource.eventList
    }

    override suspend fun getFeaturedEvent(): Event? {
        return localDataSource.eventList.firstOrNull { it.isFeatured }
    }

}
