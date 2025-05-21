package de.haukeschebitz.pokeapp.data

import de.haukeschebitz.pokeapp.data.model.PokemonDto
import de.haukeschebitz.pokeapp.network.ApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface PokeApi {
    suspend fun getPokemon(id: Int): ApiResponse<PokemonDto>
}

class PokeApiImpl(
    private val client: HttpClient,
) : PokeApi {

    companion object {
        private const val baseUrl = "https://pokeapi.co/api/v2"
        private const val pokemonEndpoint = "/pokemon"
    }

    override suspend fun getPokemon(id: Int): ApiResponse<PokemonDto> {
        return safeApiCall { client.get("$baseUrl$pokemonEndpoint/$id").body<PokemonDto>() }
    }

    private inline fun <T> safeApiCall(apiCall: () -> T): ApiResponse<T> {
        return try {
            ApiResponse.Success(data = apiCall())
        } catch (exception: Exception) {
            ApiResponse.Error(exception)
        }
    }
}
