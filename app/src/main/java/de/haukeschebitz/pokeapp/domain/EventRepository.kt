package de.haukeschebitz.pokeapp.domain

import de.haukeschebitz.pokeapp.common.Error
import de.haukeschebitz.pokeapp.common.Result
import de.haukeschebitz.pokeapp.data.EventLocalDataSource
import de.haukeschebitz.pokeapp.data.EventRemoteDataSource
import de.haukeschebitz.pokeapp.domain.mapper.toDomain
import de.haukeschebitz.pokeapp.domain.model.Event
import de.haukeschebitz.pokeapp.domain.model.Pokemon
import de.haukeschebitz.pokeapp.network.toResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

interface EventRepository {
    suspend fun getEvent(eventId: Int): Event?
    suspend fun getEventList(): Result<List<Event>, Error>
    suspend fun getFeaturedEvent(): Event?
}

class EventRepositoryImpl(
    private val localDataSource: EventLocalDataSource,
    private val remoteDataSource: EventRemoteDataSource,
    private val pokemonRepository: PokemonRepository,
    private val coroutineScope: CoroutineScope,
) : EventRepository {

    override suspend fun getEvent(eventId: Int): Event? {
        return localDataSource.eventList.firstOrNull { it.id == eventId }
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


//            .toResult {
//                it.map {
//                    val allPokemonIds = it
//                        .flatMap { it.duels }
//                        .flatMap { listOf(it.pokemon1, it.pokemon2) }
//                        .distinct() // Step 2
//
//                    val pokemonMap: Map<Int, PokemonDto> = coroutineScope {
//                        allPokemonIds
//                            .map { id ->
//                                async { id to fetchPokemon(id) } // Step 3: fetch in parallel
//                            }.awaitAll().toMap()
//                    }
//
//
//
//                    val mappedDuels = mutableListOf<Duel>()
//                    for (duel in it.duels) {
//                        val pokemon1 = coroutineScope.async { pokemonRepository.getPokemon(duel.pokemon1) }
//                        val pokemon2 = coroutineScope.async { pokemonRepository.getPokemon(duel.pokemon2) }
//                        awaitAll(pokemon1, pokemon2)
//                        mappedDuels.add(
//                            Duel(
//                                pokemon1 = pokemon1.await(),
//                                pokemon2 = pokemon2.await(),
//                                description = duel.description,
//                            )
//                    }
//
//                    it.toDomain()
//                }
//            }

    override suspend fun getFeaturedEvent(): Event? {
        return localDataSource.eventList
            .filter { it.isFeatured }
            .minByOrNull { it.dateTimestamp }
    }

}
