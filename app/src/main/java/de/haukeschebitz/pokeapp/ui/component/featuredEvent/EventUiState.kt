package de.haukeschebitz.pokeapp.ui.component.featuredEvent

import de.haukeschebitz.pokeapp.domain.model.Event
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

data class EventUiState(
    val id: Int,
    val title: String,
    val date: String,
    val location: String,
    val imageUrl: String,
)

fun Event.toUiState(): EventUiState {
    val formattedDate = try {
        val instant = Instant.ofEpochMilli(dateTimestamp)
        val zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern("EEE d MMM", Locale.getDefault())
        zonedDateTime.format(formatter)
    } catch (e: Exception) {
        dateTimestamp.toString()
    }

    return EventUiState(
        id = id,
        title = name,
        date = formattedDate,
        location = location,
        imageUrl = imageUrl
    )
}
