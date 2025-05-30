package de.haukeschebitz.pokeapp.presentation.main

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.haukeschebitz.pokeapp.domain.model.Pokemon
import de.haukeschebitz.pokeapp.ui.component.carousel.Carousel
import de.haukeschebitz.pokeapp.ui.component.carousel.CarouselItemUiState
import de.haukeschebitz.pokeapp.ui.component.carousel.EventCarousel
import de.haukeschebitz.pokeapp.ui.component.featuredEvent.EventUiState
import de.haukeschebitz.pokeapp.ui.component.featuredEvent.FeaturedEvent
import de.haukeschebitz.pokeapp.ui.theme.PokeAppTheme
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    state: MainScreenUiState,
    actions: MainScreenActions,
) {

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        when (state) {
            is MainScreenUiState.Loading -> MainScreenLoading(modifier)
            is MainScreenUiState.Error -> MainScreenError(modifier, state)
            is MainScreenUiState.Success -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top,
                    ) {
                        state.featuredEvent?.let {
                            FeaturedEventSection(
                                featuredEvent = state.featuredEvent,
                                onShowEventDetails = { actions.onShowDetailScreen(it) },
                            )
                        }

                        EventsThisWeekSection(
                            events = state.events,
                            onShowEventDetails = { actions.onShowDetailScreen(it) },
                        )

                        PopularPokemonSection(
                            popularPokemon = state.popularPokemon,
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun FeaturedEventSection(
    modifier: Modifier = Modifier,
    featuredEvent: EventUiState,
    onShowEventDetails: (eventId: Int) -> Unit,
) {
    FeaturedEvent(
        modifier = modifier,
        state = featuredEvent,
        onShowEventDetails = onShowEventDetails,
    )
}

@Composable
private fun EventsThisWeekSection(
    modifier: Modifier = Modifier,
    events: PersistentList<EventUiState>,
    onShowEventDetails: (eventId: Int) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = "Events this week",
            color = MaterialTheme.colorScheme.onBackground,
        )

        if (events.isNotEmpty()) {
            EventCarousel(
                modifier = Modifier.fillMaxWidth(),
                items = events,
                onShowEventDetails = onShowEventDetails,
            )
        } else {
            Text(
                text = "No events scheduled for this week",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun PopularPokemonSection(
    modifier: Modifier = Modifier,
    popularPokemon: PersistentList<Pokemon>,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = "Popular Pokemon",
            color = MaterialTheme.colorScheme.onBackground,
        )
        Carousel(
            modifier = Modifier.fillMaxWidth(),
            items = popularPokemon.map {
                CarouselItemUiState(
                    title = it.name,
                    imageUrl = it.imageUrl,
                )
            }.toPersistentList()
        )
    }
}

@Composable
private fun MainScreenError(
    modifier: Modifier = Modifier,
    state: MainScreenUiState.Error,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = "Error")
        Text(text = state.message)
    }
}

@Composable
private fun MainScreenLoading(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MainScreenLoadingPreview() {
    PokeAppTheme {
        MainScreen(
            state = MainScreenUiState.Loading,
            actions = MainScreenActions(),
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MainScreenErrorPreview() {
    PokeAppTheme {
        MainScreen(
            state = MainScreenUiState.Error(message = "Unknown error occurred"),
            actions = MainScreenActions(),
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MainScreenPreview() {
    PokeAppTheme {
        MainScreen(
            state = MainScreenUiState.Success(
                popularPokemon = persistentListOf(
                    Pokemon(0, "Pikachu", imageUrl = ""),
                    Pokemon(1, "Bulbasaur", imageUrl = ""),
                    Pokemon(2, "Charmander", imageUrl = ""),
                ),
                events = persistentListOf(
                    EventUiState(id = 0, title = "Event 1", date = "01. Jan", location = "Hamburg", imageUrl = ""),
                    EventUiState(id = 0, title = "Event 1", date = "24. Oct", location = "Köln", imageUrl = ""),
                ),
                featuredEvent = EventUiState(id = 0, title = "Event 1", date = "", location = "", imageUrl = ""),
            ),
            actions = MainScreenActions(),
        )
    }
}