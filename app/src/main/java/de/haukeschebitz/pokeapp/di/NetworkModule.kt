package de.haukeschebitz.pokeapp.di

import de.haukeschebitz.pokeapp.data.PokeApi
import de.haukeschebitz.pokeapp.data.PokeApiImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    factory<PokeApi> { PokeApiImpl() }

    single {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
    }
}