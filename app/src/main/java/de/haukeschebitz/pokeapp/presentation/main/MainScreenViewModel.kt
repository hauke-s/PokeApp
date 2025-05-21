package de.haukeschebitz.pokeapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.haukeschebitz.pokeapp.common.Error
import de.haukeschebitz.pokeapp.common.Result
import de.haukeschebitz.pokeapp.domain.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val repository: PokemonRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<MainScreenUiState> = MutableStateFlow(MainScreenUiState.Loading)
    val uiState: StateFlow<MainScreenUiState> = _uiState

    init {
        viewModelScope.launch {
            when (val result = repository.getPokemon(1)) {
                is Result.Success -> {
                    _uiState.value = MainScreenUiState.Success(result.data.name)
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

}
