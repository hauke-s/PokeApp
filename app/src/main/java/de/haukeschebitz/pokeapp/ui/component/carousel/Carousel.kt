package de.haukeschebitz.pokeapp.ui.component.carousel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

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
        itemWidth = 60.dp,
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
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .background(Color.Cyan),
        contentAlignment = Alignment.Center,
    ) {
        Text(state.title)

    }
}

@Preview
@Composable
private fun CarouselItemPreview() {
    CarouselItem(state = CarouselItemUiState(title = "Pikachu", imageUrl = ""))
}

@Preview(showBackground = true)
@Composable
private fun CarouselPreview() {
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