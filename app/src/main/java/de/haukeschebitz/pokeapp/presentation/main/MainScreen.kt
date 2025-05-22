package de.haukeschebitz.pokeapp.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.haukeschebitz.pokeapp.domain.model.Pokemon
import de.haukeschebitz.pokeapp.ui.component.carousel.Carousel
import de.haukeschebitz.pokeapp.ui.component.carousel.CarouselItem

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    state: MainScreenUiState,
    actions: MainScreenActions,
) {

    Surface(modifier = modifier.fillMaxSize()) {
        when (state) {
            is MainScreenUiState.Loading -> MainScreenLoading(modifier)
            is MainScreenUiState.Error -> MainScreenError(modifier, state)
            is MainScreenUiState.Success -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Button(
                            onClick = { actions.onShowDetailScreen(0) }
                        ) {
                            Text("Show detail screen")
                        }

                        Carousel(
                            modifier = Modifier.fillMaxWidth(),
                            items = state.popularPokemon.map {
                                CarouselItem(
                                    title = it.name,
                                    imageUrl = it.imageUrl,
                                )
                            }
                        )
                    }
                }
            }
        }
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
    MainScreen(
        state = MainScreenUiState.Loading,
        actions = MainScreenActions(),
    )
}

@Preview
@Composable
private fun MainScreenErrorPreview() {
    MainScreen(
        state = MainScreenUiState.Error(message = "Unknown error occurred"),
        actions = MainScreenActions(),
    )
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MainScreen(
        state = MainScreenUiState.Success(
            popularPokemon = listOf(
                Pokemon(0, "Pikachu", imageUrl = ""),
            )
        ),
        actions = MainScreenActions(),
    )
}