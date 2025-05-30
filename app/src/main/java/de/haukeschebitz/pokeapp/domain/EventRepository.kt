package de.haukeschebitz.pokeapp.domain

import de.haukeschebitz.pokeapp.common.Error
import de.haukeschebitz.pokeapp.common.Result
import de.haukeschebitz.pokeapp.data.EventLocalDataSource
import de.haukeschebitz.pokeapp.data.EventRemoteDataSource
import de.haukeschebitz.pokeapp.domain.mapper.toDomain
import de.haukeschebitz.pokeapp.domain.model.Event
import de.haukeschebitz.pokeapp.domain.model.Pokemon
import de.haukeschebitz.pokeapp.network.toResult
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

interface EventRepository {
    suspend fun getEvent(eventId: Int): Result<Event?, Error>
    suspend fun getEventList(): Result<List<Event>, Error>
    suspend fun getFeaturedEvent(): Event?
}

class EventRepositoryImpl(
    private val localDataSource: EventLocalDataSource,
    private val remoteDataSource: EventRemoteDataSource,
    private val pokemonRepository: PokemonRepository,
) : EventRepository {

    override suspend fun getEvent(eventId: Int): Result<Event?, Error> {
        return remoteDataSource.fetchEvent(eventId).toResult {
            if (it == null) return@toResult null
            val pokemonIds = it.duels
                .flatMap { listOf(it.pokemon1, it.pokemon2) }
                .distinct()

            val pokemonMap = fetchAllPokemon(pokemonIds)
            it.toDomain(pokemonMap)
        }
    }

    override suspend fun getEventList(): Result<List<Event>, Error> {
        return remoteDataSource.fetchEvents().toResult {
            val pokemonIds = it.flatMap { it.duels }
                .flatMap { listOf(it.pokemon1, it.pokemon2) }
                .distinct()

            val pokemonMap = fetchAllPokemon(pokemonIds)
            it.map { it.toDomain(pokemonMap) }
        }
    }

    private suspend fun fetchAllPokemon(pokemonIds: List<Int>): Map<Int, Pokemon> = coroutineScope {
        pokemonIds.map { id ->
            async {
                when (val result = pokemonRepository.getPokemon(id)) {
                    is Result.Success -> id to result.data
                    is Result.Error -> TODO()
                }
            }
        }.awaitAll().toMap()
    }

    override suspend fun getFeaturedEvent(): Event? {
        return localDataSource.eventList
            .filter { it.isFeatured }
            .minByOrNull { it.dateTimestamp }
    }

}
