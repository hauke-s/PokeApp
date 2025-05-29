package de.haukeschebitz.pokeapp.presentation.main

import de.haukeschebitz.pokeapp.domain.model.Pokemon
import de.haukeschebitz.pokeapp.ui.component.featuredEvent.EventUiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

sealed interface MainScreenUiState {
    data object Loading : MainScreenUiState
    data class Error(val message: String) : MainScreenUiState
    data class Success(
        val featuredEvent: EventUiState?,
        val events: PersistentList<EventUiState> = persistentListOf(),
        val popularPokemon: PersistentList<Pokemon> = persistentListOf(),
    ) : MainScreenUiState
}
