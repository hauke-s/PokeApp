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

interface GetEventsThisWeekUseCase {
    suspend fun invoke(): Result<List<Event>, Error>
}

class GetEventsThisWeekUseCaseImpl(
    private val eventRepository: EventRepository,
) : GetEventsThisWeekUseCase {

    override suspend fun invoke(): Result<List<Event>, Error> {
        try {
            val events = eventRepository.getEventList()

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

            val eventsThisWeek = events.filter { event ->
                event.dateTimestamp in startOfWeekMillis..endOfWeekMillis
            }

            return Result.Success(eventsThisWeek)
        } catch (e: DateTimeException) {
            return Result.Error(Error.UnknownError(e))
        } catch (e: Exception) {
            return Result.Error(Error.UnknownError(e))
        }
    }
}