package de.haukeschebitz.pokeapp.domain

import de.haukeschebitz.pokeapp.common.Error
import de.haukeschebitz.pokeapp.common.Result
import de.haukeschebitz.pokeapp.domain.model.Event
import java.time.DateTimeException
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters

interface GetEventsForCurrentWeekUseCase {
    suspend fun invoke(): Result<List<Event>, Error>
}

class GetEventsForCurrentWeekUseCaseImpl(
    private val eventRepository: EventRepository,
) : GetEventsForCurrentWeekUseCase {

    override suspend fun invoke(): Result<List<Event>, Error> {
        return try {
            when (val events = eventRepository.getEventList()) {
                is Result.Error -> events
                is Result.Success -> filterEventsForCurrentWeek(events.data)
            }
        } catch (e: DateTimeException) {
            Result.Error(Error.UnknownError(e))
        } catch (e: Exception) {
            Result.Error(Error.UnknownError(e))
        }
    }

    private fun filterEventsForCurrentWeek(events: List<Event>): Result<List<Event>, Error> {
        val systemZoneId = ZoneId.systemDefault()
        val today = LocalDate.now(systemZoneId)

        val startOfWeekMillis = today
            .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
            .atStartOfDay(systemZoneId)
            .toInstant()
            .toEpochMilli()

        val endOfWeekMillis = today
            .with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
            .atTime(LocalTime.MAX)
            .atZone(systemZoneId)
            .toInstant()
            .toEpochMilli()

        val eventsThisWeek = events
            .filter { event -> event.dateTimestamp in startOfWeekMillis..endOfWeekMillis }
            .sortedBy { it.dateTimestamp }

        return Result.Success(eventsThisWeek)
    }
}