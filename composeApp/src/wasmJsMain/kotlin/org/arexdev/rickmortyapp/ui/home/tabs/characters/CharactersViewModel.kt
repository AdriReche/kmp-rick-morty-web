package org.arexdev.rickmortyapp.ui.home.tabs.characters

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.arexdev.rickmortyapp.domain.GetRandomCharacter
import org.arexdev.rickmortyapp.domain.model.CharacterModel

class CharactersViewModel(val getRandomCharacter: GetRandomCharacter) : ViewModel() {
    private val _state = MutableStateFlow<CharactersState>(CharactersState())
    val state: StateFlow<CharactersState> = _state


    private val coroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    init {
        if (cachedCharacter != null) {
            _state.update { state -> state.copy(characterOfTheDay = cachedCharacter) }
        } else {
            coroutineScope.launch {
                val result = getRandomCharacter()
                cachedCharacter = result
                _state.update { state -> state.copy(characterOfTheDay = result) }

            }
        }

    }

    companion object {
        // Static variable shared among all instances because I don’t know how to save state in the ViewModel class
        private var cachedCharacter: CharacterModel? = null
    }
}