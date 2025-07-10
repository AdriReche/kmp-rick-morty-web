package org.arexdev.rickmortyapp.data.localstorage


import kotlinx.browser.window
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.arexdev.rickmortyapp.data.remote.response.CharacterResponse

class LocalStoreService() {
    fun saveCharacter(selectedDay: String, character: CharacterResponse) {
        val jsonString = Json.encodeToString(character)
        window.localStorage.setItem(selectedDay, jsonString)
    }

    fun getCharacter(key: String): CharacterResponse? {
        val jsonString = window.localStorage.getItem(key)
        if (jsonString.isNullOrBlank()) return null
        return Json.decodeFromString(CharacterResponse.serializer(), jsonString)
    }
}
