package com.example.pokemonapp.models

import com.google.gson.annotations.SerializedName

data class Pokemon(
    val id: Int,
    val name: String,
    val weight: Int,
    val height: Int,
    val types: List<PokemonType>,
    val sprites: PokemonSprite
)

data class PokemonType(
    val type: TypeName
)
data class TypeName(
    val name: String
)
data class PokemonSprite(
    @SerializedName("front_default") val pokeSprite: String
)