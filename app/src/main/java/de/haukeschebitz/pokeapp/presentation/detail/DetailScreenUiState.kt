package de.haukeschebitz.pokeapp.presentation.detail

import de.haukeschebitz.pokeapp.domain.model.Duel
import kotlinx.collections.immutable.PersistentList

data class DetailScreenUiState(
    val loading: Boolean,
    val duels: PersistentList<Duel>,
)
