package de.haukeschebitz.pokeapp.domain

import android.util.Log
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
                    Log.d("PokemonRepositoryImpl", "getPokemon: $it")
                    Result.Success(it.toDomain())
                },
                onFailure = { exception, message ->
                    Log.e("PokemonRepositoryImpl", "getPokemon: $exception")
                    Result.Error(Error.UnknownError(exception))
                },
            )
    }
}


