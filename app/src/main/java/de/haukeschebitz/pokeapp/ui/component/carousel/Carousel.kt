package de.haukeschebitz.pokeapp.ui.component.carousel

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Carousel(
    modifier: Modifier = Modifier,
    items: List<CarouselItem>,
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
        Text(items[index].title)
    }
}

@Preview(showBackground = true)
@Composable
private fun CarouselPreview() {
    Carousel(
        items = listOf(
            CarouselItem(title = "Pikachu", imageUrl = ""),
            CarouselItem(title = "Pikachu", imageUrl = ""),
            CarouselItem(title = "Pikachu", imageUrl = ""),
            CarouselItem(title = "Pikachu", imageUrl = ""),
            CarouselItem(title = "Pikachu", imageUrl = ""),
            CarouselItem(title = "Pikachu", imageUrl = ""),
        )
    )
}