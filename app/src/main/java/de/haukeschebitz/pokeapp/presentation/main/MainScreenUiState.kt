package de.haukeschebitz.pokeapp.presentation.main

import de.haukeschebitz.pokeapp.domain.model.Event
import de.haukeschebitz.pokeapp.domain.model.Pokemon
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

sealed interface MainScreenUiState {
    data object Loading : MainScreenUiState
    data class Error(val message: String) : MainScreenUiState
    data class Success(
        val featuredEvent: Event?,
        val events: PersistentList<Event> = persistentListOf(),
        val popularPokemon: PersistentList<Pokemon> = persistentListOf(),
    ) : MainScreenUiState
}
