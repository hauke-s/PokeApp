package de.haukeschebitz.pokeapp.ui.component.carousel

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import de.haukeschebitz.pokeapp.ui.theme.PokeAppTheme
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Carousel(
    modifier: Modifier = Modifier,
    items: PersistentList<CarouselItemUiState>,
) {
    HorizontalUncontainedCarousel(
        state = rememberCarouselState { items.count() },
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 16.dp),
        itemWidth = 120.dp,
        itemSpacing = 8.dp,
    ) { index ->
        CarouselItem(state = items[index])
    }
}

@Composable
private fun CarouselItem(
    modifier: Modifier = Modifier,
    state: CarouselItemUiState,
) {
    Card(
        modifier = modifier
            .aspectRatio(1f),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            AsyncImage(
                model = state.imageUrl,
                contentDescription = null,
            )

            Text(text = state.title.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
        }

    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CarouselItemPreview() {
    PokeAppTheme {
        CarouselItem(state = CarouselItemUiState(title = "Pikachu", imageUrl = ""))
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CarouselPreview() {
    PokeAppTheme {
        Carousel(
            items = persistentListOf(
                CarouselItemUiState(title = "Pikachu", imageUrl = ""),
                CarouselItemUiState(title = "Pikachu", imageUrl = ""),
                CarouselItemUiState(title = "Pikachu", imageUrl = ""),
                CarouselItemUiState(title = "Pikachu", imageUrl = ""),
                CarouselItemUiState(title = "Pikachu", imageUrl = ""),
                CarouselItemUiState(title = "Pikachu", imageUrl = ""),
            )
        )
    }
}