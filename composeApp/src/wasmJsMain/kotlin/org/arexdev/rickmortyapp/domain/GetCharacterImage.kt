package org.arexdev.rickmortyapp.domain

import org.arexdev.rickmortyapp.domain.model.ImageModel

class GetCharacterImage(val repository: Repository) {

    suspend operator fun invoke(url: String): ImageModel {
        return repository.getCharacterImage(url)
    }
}