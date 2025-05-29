package de.haukeschebitz.pokeapp.domain

import de.haukeschebitz.pokeapp.common.Error
import de.haukeschebitz.pokeapp.common.Result
import de.haukeschebitz.pokeapp.data.EventLocalDataSource
import de.haukeschebitz.pokeapp.data.EventRemoteDataSource
import de.haukeschebitz.pokeapp.domain.mapper.toDomain
import de.haukeschebitz.pokeapp.domain.model.Event
import de.haukeschebitz.pokeapp.network.toResult

interface EventRepository {
    suspend fun getEvent(eventId: Int): Event?
    suspend fun getEventList(): Result<List<Event>, Error>
    suspend fun getFeaturedEvent(): Event?
}

class EventRepositoryImpl(
    private val localDataSource: EventLocalDataSource,
    private val remoteDataSource: EventRemoteDataSource,
    private val pokemonRepository: PokemonRepository,
) : EventRepository {

    override suspend fun getEvent(eventId: Int): Event? {
        return localDataSource.eventList.firstOrNull { it.id == eventId }
    }

    override suspend fun getEventList(): Result<List<Event>, Error> {
        return remoteDataSource.fetchEvents()
            .toResult { it.map { it.toDomain() } }
    }

    override suspend fun getFeaturedEvent(): Event? {
        return localDataSource.eventList
            .filter { it.isFeatured }
            .minByOrNull { it.dateTimestamp }
    }

}
