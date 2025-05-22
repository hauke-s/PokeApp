package de.haukeschebitz.pokeapp.presentation.main

import de.haukeschebitz.pokeapp.domain.model.Pokemon

sealed interface MainScreenUiState {
    data object Loading : MainScreenUiState
    data class Error(val message: String) : MainScreenUiState
    data class Success(
        val popularPokemon: List<Pokemon>,
    ) : MainScreenUiState
}
