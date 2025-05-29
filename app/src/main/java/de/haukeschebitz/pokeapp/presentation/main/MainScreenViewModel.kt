package de.haukeschebitz.pokeapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.haukeschebitz.pokeapp.common.Error
import de.haukeschebitz.pokeapp.common.Result
import de.haukeschebitz.pokeapp.domain.GetPopularPokemonUseCase
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class MainScreenViewModel(
    private val getPopularPokemonUseCase: GetPopularPokemonUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<MainScreenUiState> = MutableStateFlow(MainScreenUiState.Loading)
    val uiState: StateFlow<MainScreenUiState> = _uiState
        .onStart { loadData() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MainScreenUiState.Loading)

    private suspend fun loadData() {
        when (val result = getPopularPokemonUseCase.invoke()) {
            is Result.Success -> {
                _uiState.value = MainScreenUiState.Success(result.data.toPersistentList())
            }

            is Result.Error -> {
                val message = when (result.error) {
                    is Error.ApiError -> result.error.message
                    Error.NoInternet -> "Please ensure device is connected to the internet"
                    is Error.UnknownError -> "Unknown error. Please share logs with support"
                }
                _uiState.value = MainScreenUiState.Error(message)
            }
        }
    }
}
