package de.haukeschebitz.pokeapp.di

import de.haukeschebitz.pokeapp.data.EventLocalDataSource
import de.haukeschebitz.pokeapp.data.MockEventLocalDataSourceImpl
import de.haukeschebitz.pokeapp.domain.EventRepository
import de.haukeschebitz.pokeapp.domain.EventRepositoryImpl
import de.haukeschebitz.pokeapp.domain.GetEventsThisWeekUseCase
import de.haukeschebitz.pokeapp.domain.GetEventsThisWeekUseCaseImpl
import de.haukeschebitz.pokeapp.domain.GetPopularPokemonUseCase
import de.haukeschebitz.pokeapp.domain.GetPopularPokemonUseCaseImpl
import de.haukeschebitz.pokeapp.domain.PokemonRepository
import de.haukeschebitz.pokeapp.domain.PokemonRepositoryImpl
import de.haukeschebitz.pokeapp.presentation.detail.DetailScreenViewModel
import de.haukeschebitz.pokeapp.presentation.main.MainScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    factory<PokemonRepository> {
        PokemonRepositoryImpl(
            pokeApi = get(),
        )
    }

    factory<EventRepository> {
        EventRepositoryImpl(
            localDataSource = get()
        )
    }

    factory<EventLocalDataSource> { MockEventLocalDataSourceImpl() }

    factory<GetPopularPokemonUseCase> {
        GetPopularPokemonUseCaseImpl(
            pokemonRepository = get()
        )
    }

    factory<GetEventsThisWeekUseCase> {
        GetEventsThisWeekUseCaseImpl(
            eventRepository = get()
        )
    }

    viewModelOf(::MainScreenViewModel)

    viewModel {
        DetailScreenViewModel(
            eventId = get(),
            eventRepository = get(),
        )
    }

}