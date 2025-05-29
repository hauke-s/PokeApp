package de.haukeschebitz.pokeapp.presentation.main

import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.haukeschebitz.pokeapp.domain.model.Event
import de.haukeschebitz.pokeapp.domain.model.Pokemon
import de.haukeschebitz.pokeapp.domain.model.defaultEvent
import de.haukeschebitz.pokeapp.ui.component.carousel.Carousel
import de.haukeschebitz.pokeapp.ui.component.carousel.CarouselItemUiState
import de.haukeschebitz.pokeapp.ui.component.featuredEvent.FeaturedEvent
import de.haukeschebitz.pokeapp.ui.component.featuredEvent.toUiState
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
        color = Color.Black,
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
                        modifier = modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceBetween,
                    ) {
                        state.featuredEvent?.let {
                            FeaturedEventSection(
//                                modifier = Modifier.weight(4f),
                                featuredEvent = state.featuredEvent,
                            )
                        }

                        EventsThisWeekSection(
//                            modifier = Modifier.weight(3f),
                            events = state.events,
                        )

                        PopularPokemonSection(
//                            modifier = Modifier.weight(3f),
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
    featuredEvent: Event,
) {
    FeaturedEvent(
        modifier = modifier,
        state = featuredEvent.toUiState(),
    )

}

@Composable
private fun EventsThisWeekSection(
    modifier: Modifier = Modifier,
    events: PersistentList<Event>,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Events this week",
            color = Color.White,
        )

        if (events.isNotEmpty()) {
            Carousel(
                modifier = Modifier.fillMaxWidth(),
                items = events.map {
                    CarouselItemUiState(
                        title = it.name,
                        imageUrl = it.imageUrl,
                    )
                }.toPersistentList()
            )
        } else {
            Text(
                text = "No events scheduled for this week",
                color = Color.White,
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
            color = Color.White,
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
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Preview
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
@Composable
private fun MainScreenErrorPreview() {
    PokeAppTheme {
        MainScreen(
            state = MainScreenUiState.Error(message = "Unknown error occurred"),
            actions = MainScreenActions(),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    PokeAppTheme {
        MainScreen(
            state = MainScreenUiState.Success(
                popularPokemon = persistentListOf(
                    Pokemon(0, "Pikachu", imageUrl = ""),
                ),
                events = persistentListOf(
                    defaultEvent.copy(id = 0),
                    defaultEvent.copy(id = 1, name = "Event 1"),
                ),
                featuredEvent = defaultEvent.copy(id = 2, name = "Featured Event", isFeatured = true),
            ),
            actions = MainScreenActions(),
        )
    }
}