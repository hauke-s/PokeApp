package de.haukeschebitz.pokeapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventDto(
    @SerialName("id")
    val id: Int,

    @SerialName("title")
    val title: String,

    @SerialName("date_timestamp")
    val dateTimestamp: Long,

    @SerialName("location")
    val location: String,

    @SerialName("image_url")
    val imageUrl: String,

    @SerialName("duels")
    val duels: List<DuelDto>,

    @SerialName("is_featured")
    val isFeatured: Boolean,
)
