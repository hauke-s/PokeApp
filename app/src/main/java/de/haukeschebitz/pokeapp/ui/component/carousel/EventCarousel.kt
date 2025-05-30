package de.haukeschebitz.pokeapp.ui.component.carousel

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import de.haukeschebitz.pokeapp.ui.component.featuredEvent.EventUiState
import de.haukeschebitz.pokeapp.ui.theme.PokeAppTheme
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventCarousel(
    modifier: Modifier = Modifier,
    items: PersistentList<EventUiState>,
    onShowEventDetails: (eventId: Int) -> Unit,
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
        EventCarouselItem(
            state = items[index],
            onShowEventDetails = onShowEventDetails,
        )
    }
}

@Composable
private fun EventCarouselItem(
    modifier: Modifier = Modifier,
    state: EventUiState,
    onShowEventDetails: (eventId: Int) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onShowEventDetails(state.id) }
    ) {
        Card(
            modifier = modifier.aspectRatio(1f),
            shape = RoundedCornerShape(8.dp),
        ) {
            AsyncImage(
                model = state.imageUrl,
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
        }

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = state.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = state.date,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
            )
            Text(
                text = state.location,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
            )
        }

    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CarouselItemPreview() {
    PokeAppTheme {
        EventCarouselItem(
            state = EventUiState(
                id = 0,
                title = "Event 1",
                date = "",
                location = "",
                imageUrl = "",
            ),
            onShowEventDetails = { },
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CarouselPreview() {
    PokeAppTheme {
        EventCarousel(
            items = persistentListOf(
                EventUiState(id = 0, title = "Event 1", date = "", location = "", imageUrl = ""),
                EventUiState(id = 1, title = "Event 2", date = "", location = "", imageUrl = ""),
                EventUiState(id = 2, title = "Event 3", date = "", location = "", imageUrl = ""),
                EventUiState(id = 3, title = "Event 4", date = "", location = "", imageUrl = ""),
                EventUiState(id = 4, title = "Event 5", date = "", location = "", imageUrl = ""),
                EventUiState(id = 5, title = "Event 6", date = "", location = "", imageUrl = ""),
            ),
            onShowEventDetails = { },
        )
    }
}