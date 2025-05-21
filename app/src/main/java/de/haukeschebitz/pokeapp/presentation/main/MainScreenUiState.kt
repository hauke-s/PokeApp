package de.haukeschebitz.pokeapp.presentation.main

sealed interface MainScreenUiState {
    data object Loading : MainScreenUiState
    data class Error(val message: String) : MainScreenUiState
    data class Success(
        val data: String,
    ) : MainScreenUiState
}
