package de.haukeschebitz.pokeapp.di

import de.haukeschebitz.pokeapp.common.SessionStateHolder
import de.haukeschebitz.pokeapp.common.SessionStateHolderImpl
import de.haukeschebitz.pokeapp.data.EventLocalDataSource
import de.haukeschebitz.pokeapp.data.EventRemoteDataSource
import de.haukeschebitz.pokeapp.data.MockEventLocalDataSourceImpl
import de.haukeschebitz.pokeapp.data.MockEventRemoteDataSourceImpl
import de.haukeschebitz.pokeapp.domain.EventRepository
import de.haukeschebitz.pokeapp.domain.EventRepositoryImpl
import de.haukeschebitz.pokeapp.domain.GetEventsForCurrentWeekUseCase
import de.haukeschebitz.pokeapp.domain.GetEventsForCurrentWeekUseCaseImpl
import de.haukeschebitz.pokeapp.domain.GetFeaturedEventUseCase
import de.haukeschebitz.pokeapp.domain.GetFeaturedEventUseCaseImpl
import de.haukeschebitz.pokeapp.domain.GetPopularPokemonUseCase
import de.haukeschebitz.pokeapp.domain.GetPopularPokemonUseCaseImpl
import de.haukeschebitz.pokeapp.domain.PokemonRepository
import de.haukeschebitz.pokeapp.domain.PokemonRepositoryImpl
import de.haukeschebitz.pokeapp.presentation.detail.DetailScreenViewModel
import de.haukeschebitz.pokeapp.presentation.main.MainScreenViewModel
import org.koin.android.ext.koin.androidContext
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
            localDataSource = get(),
            remoteDataSource = get(),
            pokemonRepository = get(),
        )
    }

    factory<EventLocalDataSource> { MockEventLocalDataSourceImpl() }
    factory<EventRemoteDataSource> { MockEventRemoteDataSourceImpl(androidContext()) }

    factory<GetPopularPokemonUseCase> {
        GetPopularPokemonUseCaseImpl(
            pokemonRepository = get()
        )
    }

    factory<GetEventsForCurrentWeekUseCase> {
        GetEventsForCurrentWeekUseCaseImpl(
            eventRepository = get()
        )
    }

    factory<GetFeaturedEventUseCase> {
        GetFeaturedEventUseCaseImpl(
            eventRepository = get()
        )
    }

    single<SessionStateHolder> { SessionStateHolderImpl() }

    viewModelOf(::MainScreenViewModel)
    viewModelOf(::DetailScreenViewModel)

}
