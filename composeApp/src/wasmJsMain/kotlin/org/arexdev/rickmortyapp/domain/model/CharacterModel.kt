package org.arexdev.rickmortyapp.domain.model


import kotlinx.serialization.Serializable

@Serializable
data class CharacterModel(
    val id: Int,
    val name: String,
    val isAlive: Boolean,
    val image: String,
    val species: String,
    val gender: String,
    val origin: String,
    val episodes: List<String>
)