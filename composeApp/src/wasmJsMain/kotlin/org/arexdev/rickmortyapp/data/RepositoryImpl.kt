package org.arexdev.rickmortyapp.data

import org.arexdev.rickmortyapp.data.remote.ApiServiceKtor
import org.arexdev.rickmortyapp.domain.Repository
import org.arexdev.rickmortyapp.domain.model.CharacterModel
import org.arexdev.rickmortyapp.domain.model.ImageModel

class RepositoryImpl(private val apiService: ApiServiceKtor) : Repository {
    override suspend fun getSingleCharacter(id: String): CharacterModel {
        return apiService.getSingleCharacter(id).toDomainModel()
    }

    override suspend fun getCharacterImage(url: String): ImageModel {
        return ImageModel(apiService.getCharacterImage(url))
    }
}
