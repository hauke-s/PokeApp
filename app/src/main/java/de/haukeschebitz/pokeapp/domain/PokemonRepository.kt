package de.haukeschebitz.pokeapp.domain

import de.haukeschebitz.pokeapp.common.Error
import de.haukeschebitz.pokeapp.common.Result
import de.haukeschebitz.pokeapp.data.PokeApi
import de.haukeschebitz.pokeapp.domain.mapper.toDomain
import de.haukeschebitz.pokeapp.domain.model.Pokemon
import de.haukeschebitz.pokeapp.network.fold

interface PokemonRepository {
    suspend fun getPokemon(id: Int): Result<Pokemon, Error>
}

class PokemonRepositoryImpl(
    private val pokeApi: PokeApi,
) : PokemonRepository {

    override suspend fun getPokemon(id: Int): Result<Pokemon, Error> {
        return pokeApi.getPokemon(id)
            .fold(
                onSuccess = {
                    Result.Success(it.toDomain())
                },
                onFailure = { exception, message ->
                    Result.Error(Error.UnknownError(exception))
                },
            )
    }

}
