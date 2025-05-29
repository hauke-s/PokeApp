package de.haukeschebitz.pokeapp.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.haukeschebitz.pokeapp.domain.EventRepository
import kotlinx.collections.immutable.immutableListOf
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class DetailScreenViewModel(
    private val eventId: Int,
    private val eventRepository: EventRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailScreenUiState(duels = persistentListOf()))
    val uiState: StateFlow<DetailScreenUiState> = _uiState
        .onStart { loadData() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            DetailScreenUiState(duels = persistentListOf())
        )

    private suspend fun loadData() {
        eventRepository.getEvent(eventId)?.duels?.let { duels ->
            _uiState.update { DetailScreenUiState(duels) }
        }
    }
}
