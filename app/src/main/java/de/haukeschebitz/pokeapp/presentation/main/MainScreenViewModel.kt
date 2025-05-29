package de.haukeschebitz.pokeapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.haukeschebitz.pokeapp.common.Error
import de.haukeschebitz.pokeapp.common.Result
import de.haukeschebitz.pokeapp.domain.GetEventsThisWeekUseCase
import de.haukeschebitz.pokeapp.domain.GetPopularPokemonUseCase
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class MainScreenViewModel(
    private val getPopularPokemonUseCase: GetPopularPokemonUseCase,
    private val getEventsThisWeekUseCase: GetEventsThisWeekUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<MainScreenUiState> = MutableStateFlow(MainScreenUiState.Loading)
    val uiState: StateFlow<MainScreenUiState> = _uiState
        .onStart { loadData() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MainScreenUiState.Loading)

    private suspend fun loadData() {
        val deferredPokemon = viewModelScope.async { getPopularPokemonUseCase.invoke() }
        val deferredEvents = viewModelScope.async { getEventsThisWeekUseCase.invoke() }

        val fetchedPokemon = deferredPokemon.await()
        val fetchedEvents = deferredEvents.await()

        val error = listOf(fetchedPokemon, fetchedEvents).find { it is Result.Error }

        if (error != null) {
            val errorType = (error as Result.Error).error
            val dataSourceName = if (error == fetchedPokemon) "Pokémon data" else "events data"

            val message = when (errorType) {
                is Error.ApiError -> errorType.message
                Error.NoInternet -> "Failed to load $dataSourceName: Please ensure the device is connected to the internet."
                is Error.UnknownError -> "Failed to load $dataSourceName: An unknown error occurred. Please share logs with support."
            }
            _uiState.value = MainScreenUiState.Error(message)
        } else {
            _uiState.value = MainScreenUiState.Success(
                popularPokemon = (fetchedPokemon as Result.Success).data.toPersistentList(),
                events = (fetchedEvents as Result.Success).data.toPersistentList()
            )
        }

//        when (val result = getPopularPokemonUseCase.invoke()) {
//            is Result.Success -> {
//                _uiState.value = MainScreenUiState.Success(
//                    popularPokemon = result.data.toPersistentList()
//                )
////                _uiState.update { it.copy(popularPokemon = result.data.toPersistentList()) }
//            }
//
//            is Result.Error -> {
//                val message = when (result.error) {
//                    is Error.ApiError -> result.error.message
//                    Error.NoInternet -> "Please ensure device is connected to the internet"
//                    is Error.UnknownError -> "Unknown error. Please share logs with support"
//                }
//                _uiState.value = MainScreenUiState.Error(message)
//            }
//        }
//
    }
}
