package de.haukeschebitz.pokeapp.presentation.main

data class MainScreenActions(
    val onShowDetailScreen: (eventId: Int) -> Unit = {},
)