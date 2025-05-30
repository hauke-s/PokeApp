package de.haukeschebitz.pokeapp.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.haukeschebitz.pokeapp.domain.model.Duel
import de.haukeschebitz.pokeapp.domain.model.Pokemon
import kotlinx.collections.immutable.persistentListOf

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    state: DetailScreenUiState,
    onScreenShown: () -> Unit = {},
) {
    LaunchedEffect(true) {
        onScreenShown()
    }

    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            if (state.loading) {
                CircularProgressIndicator()
                return@Surface
            }

            state.duels.forEach {
                Text(text = "${it.pokemon1.name} VS ${it.pokemon2.name}")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailScreenPreview() {
    DetailScreen(
        state = DetailScreenUiState(
            loading = false,
            duels = persistentListOf(
                Duel(Pokemon(id = 0, name = "Pikachu"), Pokemon(id = 1, name = "Pikachu")),
                Duel(Pokemon(id = 2, name = "Pikachu"), Pokemon(id = 3, name = "Pikachu")),
                Duel(Pokemon(id = 4, name = "Pikachu"), Pokemon(id = 5, name = "Pikachu")),
            )
        )
    )
}