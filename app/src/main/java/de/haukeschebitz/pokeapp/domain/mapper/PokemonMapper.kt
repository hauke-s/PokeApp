package de.haukeschebitz.pokeapp.domain.mapper

import de.haukeschebitz.pokeapp.data.model.PokemonDto
import de.haukeschebitz.pokeapp.domain.model.Pokemon

fun PokemonDto.toDomain(): Pokemon = Pokemon(
    id = id,
    name = name,
    imageUrl = sprites.frontDefault,
)