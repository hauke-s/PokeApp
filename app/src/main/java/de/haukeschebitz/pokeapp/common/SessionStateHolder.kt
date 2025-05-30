package de.haukeschebitz.pokeapp.common

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface SessionStateHolder {
    val selectedEvent: StateFlow<Int?>
    fun updateSelectedEvent(eventId: Int)
}

class SessionStateHolderImpl : SessionStateHolder {

    private val _selectedEvent = MutableStateFlow<Int?>(null)
    override val selectedEvent = _selectedEvent.asStateFlow()

    override fun updateSelectedEvent(eventId: Int) {
        _selectedEvent.update { eventId }
    }

}