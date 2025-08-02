package org.arexdev.rickmortyapp.data.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json
import org.arexdev.rickmortyapp.data.remote.response.CharacterResponse
import org.arexdev.rickmortyapp.data.remote.response.CharactersWrapperResponse
import org.arexdev.rickmortyapp.data.remote.response.EpisodeResponse
import org.arexdev.rickmortyapp.data.remote.response.EpisodesWrapperResponse

class ApiServiceKtor() {

    companion object {
        private const val BASE_URL = "https://rickandmortyapi.com/api/"

        private const val EPISODES_URL = "$BASE_URL/episode/"
    }

    private val json = Json { ignoreUnknownKeys = true }

    val client = HttpClient()

    suspend fun getSingleCharacter(id: String): CharacterResponse {
        val url = BASE_URL + "character/$id"
        return try {
            val response: String = client.get(url).body()
            json.decodeFromString(CharacterResponse.serializer(), response)
        } catch (e: Exception) {
            throw Exception("Error fetching character: ${e.message}", e)
        }

    }

    suspend fun getCharacterImage(url: String): ByteArray {
        return try {
            client.get(url).body()
        } catch (e: Exception) {
            throw Exception("Error fetching image: ${e.message}", e)
        }
    }

    suspend fun getAllCharacters(page: Int): CharactersWrapperResponse {
        try {
            val response: String = client.get(BASE_URL + "character/") {
                parameter("page", page)
            }.body()
            return json.decodeFromString(CharactersWrapperResponse.serializer(), response)
        } catch (e: Exception) {
            throw Exception("Error fetching characters: ${e.message}", e)
        }
    }


    suspend fun getAllEpisodes(page: Int): EpisodesWrapperResponse {
        try {
            val response: String = client.get(EPISODES_URL) {
                parameter("page", page)
            }.body()
            return json.decodeFromString(EpisodesWrapperResponse.serializer(), response)
        } catch (e: Exception) {
            throw Exception("Error fetching episodes: ${e.message}", e)
        }
    }

    suspend fun getEpisodes(episodes: String): List<EpisodeResponse> {
        val response: String = client.get(EPISODES_URL + episodes).body()
        return json.decodeFromString(response)
    }

    suspend fun getSingleEpisode(episodeId: String): EpisodeResponse {
        val response: String = client.get(EPISODES_URL + episodeId).body()
        return json.decodeFromString(response)
    }
}
