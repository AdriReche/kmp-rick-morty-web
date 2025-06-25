package org.arexdev.rickmortyapp.domain

import org.arexdev.rickmortyapp.domain.model.CharacterModel
import org.arexdev.rickmortyapp.domain.model.ImageModel

interface Repository {
    suspend fun getSingleCharacter(id: String): CharacterModel
    suspend fun getCharacterImage(url: String): ImageModel
}