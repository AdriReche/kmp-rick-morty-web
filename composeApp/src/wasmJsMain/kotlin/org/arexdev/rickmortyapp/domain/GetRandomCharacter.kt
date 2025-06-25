package org.arexdev.rickmortyapp.domain

import org.arexdev.rickmortyapp.domain.model.CharacterModel

class GetRandomCharacter(val repository: Repository) {

    suspend operator fun invoke(): CharacterModel {
        val randomId: Int = (1..826).random()
        return repository.getSingleCharacter(randomId.toString())
    }
}