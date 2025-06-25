package org.arexdev.rickmortyapp.ui.home.tabs.characters

import org.arexdev.rickmortyapp.domain.model.CharacterModel

data class CharactersState(
    val characterOfTheDay: CharacterModel? = null,
)