package de.haukeschebitz.pokeapp.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.haukeschebitz.pokeapp.common.Result
import de.haukeschebitz.pokeapp.common.SessionStateHolder
import de.haukeschebitz.pokeapp.domain.EventRepository
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailScreenViewModel(
    private val eventId: Int,
    sessionStateHolder: SessionStateHolder,
    private val eventRepository: EventRepository,
) : ViewModel() {

    private val selectedEvent = sessionStateHolder.selectedEvent

    private val _uiState = MutableStateFlow(DetailScreenUiState(loading = true, duels = persistentListOf()))
    val uiState: StateFlow<DetailScreenUiState> = _uiState
//        .onStart { loadData() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            DetailScreenUiState(loading = true, duels = persistentListOf())
        )

    private suspend fun loadData() {
        _uiState.update { it.copy(loading = true) }
        val eventId = selectedEvent.firstOrNull() ?: return
        when (val event = eventRepository.getEvent(eventId)) {
            is Result.Error -> TODO()
            is Result.Success -> {
                event.data?.duels?.let { duels ->
                    _uiState.update { it.copy(duels = duels.toPersistentList()) }
                }
            }
        }
        _uiState.update { it.copy(loading = false) }
    }

    fun onScreenShown() {
        viewModelScope.launch { loadData() }
    }
}
