package de.haukeschebitz.pokeapp.domain

import android.util.Log
import de.haukeschebitz.pokeapp.common.Error
import de.haukeschebitz.pokeapp.common.Result
import de.haukeschebitz.pokeapp.domain.model.Pokemon
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

interface GetPopularPokemonUseCase {
    suspend operator fun invoke(): Result<List<Pokemon>, Error>
}

class GetPopularPokemonUseCaseImpl(
    private val pokemonRepository: PokemonRepository,
) : GetPopularPokemonUseCase {

    override suspend fun invoke(): Result<List<Pokemon>, Error> = coroutineScope {
        val pokemonResults = mutableListOf<Result<Pokemon, Error>>()
        val deferredPokemon = (1..10).map {
            async { pokemonRepository.getPokemon(it) }
        }

        pokemonResults.addAll(deferredPokemon.awaitAll())

        val pokemonList = mutableListOf<Pokemon>()

        for (result in pokemonResults) {
            when (result) {
                is Result.Success -> {
                    pokemonList.add(result.data)
                }

                is Result.Error -> {
                    Log.e("GetPopularPokemonUseCaseImpl", "invoke: ${result.error}")
                }
            }
        }

        return@coroutineScope Result.Success(pokemonList)
    }

}