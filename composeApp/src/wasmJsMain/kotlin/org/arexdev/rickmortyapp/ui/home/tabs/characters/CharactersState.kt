package org.arexdev.rickmortyapp.ui.home.tabs.characters

import org.arexdev.rickmortyapp.domain.model.CharacterModel

data class CharactersState(
    val characterOfTheDay: CharacterModel? = null,
    val characters: CharactersUiState = CharactersUiState(),
)

data class CharactersUiState(
    val items: List<CharacterModel> = emptyList(),
    val loading: Boolean = false,
    val endReached: Boolean = false,
    val error: String? = null
)