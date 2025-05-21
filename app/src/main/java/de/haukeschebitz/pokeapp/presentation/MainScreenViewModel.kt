package de.haukeschebitz.pokeapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.haukeschebitz.pokeapp.domain.PokemonRepository
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val repository: PokemonRepository,
) : ViewModel() {

    init {
        viewModelScope.launch {
            val result = repository.getPokemon(0)
            println(result)
        }
    }

}