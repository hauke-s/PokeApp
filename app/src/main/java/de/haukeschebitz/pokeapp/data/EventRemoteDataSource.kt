package de.haukeschebitz.pokeapp.data

import android.content.Context
import de.haukeschebitz.pokeapp.R
import de.haukeschebitz.pokeapp.data.model.EventDto
import de.haukeschebitz.pokeapp.network.ApiResponse
import kotlinx.serialization.json.Json

interface EventRemoteDataSource {
    suspend fun fetchEvent(eventId: Int): ApiResponse<EventDto?>
    suspend fun fetchEvents(): ApiResponse<List<EventDto>>
}

class MockEventRemoteDataSourceImpl(
    private val context: Context,
) : EventRemoteDataSource {

    override suspend fun fetchEvent(eventId: Int): ApiResponse<EventDto?> {
        return try {
            val inputStream = context.resources.openRawResource(R.raw.events)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val eventDto: EventDto? = Json.decodeFromString<List<EventDto>>(jsonString)
                .firstOrNull { it.id == eventId }
            ApiResponse.Success(eventDto)
        } catch (e: Exception) {
            ApiResponse.Error(e)
        }
    }

    override suspend fun fetchEvents(): ApiResponse<List<EventDto>> {
        return try {
            val inputStream = context.resources.openRawResource(R.raw.events)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val eventDtos: List<EventDto> = Json.decodeFromString(jsonString)
            ApiResponse.Success(eventDtos)
        } catch (e: Exception) {
            ApiResponse.Error(e)
        }
    }

}
