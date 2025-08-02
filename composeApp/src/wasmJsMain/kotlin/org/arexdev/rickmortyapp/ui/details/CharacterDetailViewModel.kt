package org.arexdev.rickmortyapp.ui.details

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.arexdev.rickmortyapp.domain.Repository
import org.arexdev.rickmortyapp.domain.model.CharacterModel

class CharacterDetailViewModel(character: CharacterModel, private val repository: Repository) : ViewModel() {
    private val _uiState =
        MutableStateFlow<CharacterDetailState>(CharacterDetailState(character))

    val uiState: StateFlow<CharacterDetailState> = _uiState

    private val coroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())


    init {
        getEpisodesForCharacter(character.episodes)
    }

    private fun getEpisodesForCharacter(episodes: List<String>) {
        coroutineScope.launch {
            val result = repository.getEpisodesForCharacter(episodes)
            _uiState.update { it.copy(episodes = result) }

        }
    }

}