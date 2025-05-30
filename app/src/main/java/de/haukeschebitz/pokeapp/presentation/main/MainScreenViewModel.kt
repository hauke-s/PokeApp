package de.haukeschebitz.pokeapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.haukeschebitz.pokeapp.common.Error
import de.haukeschebitz.pokeapp.common.Result
import de.haukeschebitz.pokeapp.common.SessionStateHolder
import de.haukeschebitz.pokeapp.domain.GetEventsForCurrentWeekUseCase
import de.haukeschebitz.pokeapp.domain.GetFeaturedEventUseCase
import de.haukeschebitz.pokeapp.domain.GetPopularPokemonUseCase
import de.haukeschebitz.pokeapp.ui.component.featuredEvent.toUiState
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class MainScreenViewModel(
    private val getFeaturedEventUseCase: GetFeaturedEventUseCase,
    private val getPopularPokemonUseCase: GetPopularPokemonUseCase,
    private val getEventsForCurrentWeekUseCase: GetEventsForCurrentWeekUseCase,
    private val sessionStateHolder: SessionStateHolder,
) : ViewModel() {

    private val _uiState: MutableStateFlow<MainScreenUiState> = MutableStateFlow(MainScreenUiState.Loading)
    val uiState: StateFlow<MainScreenUiState> = _uiState
        .onStart { loadData() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MainScreenUiState.Loading)

    private suspend fun loadData() {
        val deferredFeaturedEvent = viewModelScope.async { getFeaturedEventUseCase.invoke() }
        val deferredPokemon = viewModelScope.async { getPopularPokemonUseCase.invoke() }
        val deferredEvents = viewModelScope.async { getEventsForCurrentWeekUseCase.invoke() }

        val fetchedFeaturedEvent = deferredFeaturedEvent.await()
        val fetchedPokemon = deferredPokemon.await()
        val fetchedEvents = deferredEvents.await()

        val resultsWithSource = listOf(
            "Featured Event" to fetchedFeaturedEvent,
            "Popular Pokemon" to fetchedPokemon,
            "Weekly Events" to fetchedEvents
        )

        val error = resultsWithSource.find { (_, result) -> result is Result.Error }

        if (error != null) {
            val (dataSourceName, errorResult) = error

            val message = when (val errorType = (errorResult as Result.Error).error) {
                is Error.ApiError -> errorType.message
                Error.NoInternet -> "Failed to load $dataSourceName: Please ensure the device is connected to the internet."
                is Error.UnknownError -> "Failed to load $dataSourceName: An unknown error occurred. Please share logs with support. ${errorType.throwable}"
            }
            _uiState.value = MainScreenUiState.Error(message)
        } else {
            _uiState.value = MainScreenUiState.Success(
                featuredEvent = (fetchedFeaturedEvent as Result.Success).data?.toUiState(),
                popularPokemon = (fetchedPokemon as Result.Success).data.toPersistentList(),
                events = (fetchedEvents as Result.Success).data.map { it.toUiState() }.toPersistentList()
            )
        }

    }

    fun onShowDetailScreen(eventId: Int) {
        sessionStateHolder.updateSelectedEvent(eventId)
    }
}
