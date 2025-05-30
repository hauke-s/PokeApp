package de.haukeschebitz.pokeapp.presentation.detail

import kotlinx.collections.immutable.PersistentList

data class DetailScreenUiState(
    val loading: Boolean,
    val duels: PersistentList<DuelUiState>,
)
