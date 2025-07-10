package org.arexdev.rickmortyapp.data.remote.response

import kotlinx.serialization.Serializable
import org.arexdev.rickmortyapp.domain.model.CharacterModel

@Serializable
data class CharacterResponse(
    val id: Int,
    val name: String,
    val status: String,
    val image: String
) {
    fun toDomainModel(): CharacterModel {
        return CharacterModel(
            id = id,
            name = name,
            isAlive = status.lowercase() == "alive",
            image = image
        )
    }

    companion object {
        fun toResponse(characterModel: CharacterModel): CharacterResponse {
            return CharacterResponse(
                id = characterModel.id,
                name = characterModel.name,
                status = if (characterModel.isAlive) "Alive" else "Dead",
                image = characterModel.image
            )
        }
    }
}