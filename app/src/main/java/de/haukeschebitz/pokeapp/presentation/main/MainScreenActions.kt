package de.haukeschebitz.pokeapp.presentation.main

data class MainScreenActions(
    val onShowDetailScreen: (pokemonId: Int) -> Unit = {},
)