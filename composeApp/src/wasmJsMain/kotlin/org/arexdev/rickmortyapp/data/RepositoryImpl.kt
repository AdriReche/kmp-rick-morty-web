package org.arexdev.rickmortyapp.data

import org.arexdev.rickmortyapp.data.localstorage.LocalStoreService
import org.arexdev.rickmortyapp.data.remote.ApiServiceKtor
import org.arexdev.rickmortyapp.data.remote.response.CharacterResponse
import org.arexdev.rickmortyapp.domain.Repository
import org.arexdev.rickmortyapp.domain.model.CharacterModel
import org.arexdev.rickmortyapp.domain.model.EpisodeModel
import org.arexdev.rickmortyapp.domain.model.ImageModel

class RepositoryImpl(private val apiService: ApiServiceKtor, private val localStoreService: LocalStoreService) :
    Repository {
    override suspend fun getSingleCharacter(id: String): CharacterModel {
        return apiService.getSingleCharacter(id).toDomainModel()
    }

    override suspend fun getCharacterImage(url: String): ImageModel {
        return ImageModel(apiService.getCharacterImage(url))
    }

    override suspend fun getAllCharacters(page: Int): List<CharacterModel> =
        apiService.getAllCharacters(page).results.map { it.toDomainModel() }

    override suspend fun saveCharacterDB(
        selectedDay: String,
        character: CharacterModel
    ) =
        localStoreService.saveCharacter(selectedDay, CharacterResponse.toResponse(character))

    override suspend fun getCharacterDB(selectedDay: String): CharacterModel? =
        localStoreService.getCharacter(selectedDay)?.toDomainModel()

    override suspend fun getAllEpisodes(page: Int): List<EpisodeModel> =
        apiService.getAllEpisodes(page).results.map { it.toDomainModel() }

}
