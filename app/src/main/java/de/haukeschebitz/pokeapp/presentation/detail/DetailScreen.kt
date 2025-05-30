package de.haukeschebitz.pokeapp.presentation.detail

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import de.haukeschebitz.pokeapp.R
import de.haukeschebitz.pokeapp.ui.theme.PokeAppTheme
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
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            if (state.loading) {
                CircularProgressIndicator()
                return@Surface
            }

            if (state.duels.isEmpty()) {
                Text(text = "No fights announced yet")
                return@Surface
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                state.duels.forEach {
                    DuelCard(state = it)
                }
            }
        }
    }
}

@Composable
private fun DuelCard(
    modifier: Modifier = Modifier,
    state: DuelUiState,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            AsyncImage(
                model = state.pokemon1.imageUrl,
                contentDescription = null,
            )

            Text(
                text = state.pokemon1.name,
            )

            Text(
                text = stringResource(R.string.detail_screen_vs),
                color = MaterialTheme.colorScheme.primary,
            )

            Text(
                text = state.pokemon2.name,
            )

            AsyncImage(
                model = state.pokemon2.imageUrl,
                contentDescription = null,
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DuelPreview() {
    PokeAppTheme {
        DuelCard(
            state = DuelUiState(
                pokemon1 = PokemonUiState(id = 0, name = "Pikachu"),
                pokemon2 = PokemonUiState(id = 1, name = "Bulbasaur"),
            )
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DetailScreenPreview() {
    PokeAppTheme {
        DetailScreen(
            state = DetailScreenUiState(
                loading = false,
                duels = persistentListOf(
                    DuelUiState(PokemonUiState(id = 0, name = "Pikachu"), PokemonUiState(id = 1, name = "Pikachu")),
                    DuelUiState(PokemonUiState(id = 2, name = "Pikachu"), PokemonUiState(id = 3, name = "Pikachu")),
                    DuelUiState(PokemonUiState(id = 4, name = "Pikachu"), PokemonUiState(id = 5, name = "Pikachu")),
                )
            )
        )
    }
}
