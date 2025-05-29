package de.haukeschebitz.pokeapp.presentation.main

import de.haukeschebitz.pokeapp.domain.model.Pokemon
import kotlinx.collections.immutable.PersistentList

sealed interface MainScreenUiState {
    data object Loading : MainScreenUiState
    data class Error(val message: String) : MainScreenUiState
    data class Success(
        val popularPokemon: PersistentList<Pokemon>,
    ) : MainScreenUiState
}
