package de.haukeschebitz.pokeapp.domain

import de.haukeschebitz.pokeapp.common.Error
import de.haukeschebitz.pokeapp.common.Result
import de.haukeschebitz.pokeapp.domain.model.Event

interface GetFeaturedEventUseCase {
    suspend fun invoke(): Result<Event?, Error>
}

class GetFeaturedEventUseCaseImpl(
    private val eventRepository: EventRepository,
) : GetFeaturedEventUseCase {

    override suspend fun invoke(): Result<Event?, Error> {
        return Result.Success(eventRepository.getFeaturedEvent())

    }

}