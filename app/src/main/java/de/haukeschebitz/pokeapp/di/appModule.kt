package de.haukeschebitz.pokeapp.di

import de.haukeschebitz.pokeapp.domain.PokemonRepository
import de.haukeschebitz.pokeapp.domain.PokemonRepositoryImpl
import de.haukeschebitz.pokeapp.presentation.main.MainScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    factory<PokemonRepository> {
        PokemonRepositoryImpl(
            pokeApi = get(),
        )
    }

    viewModelOf(::MainScreenViewModel)

}