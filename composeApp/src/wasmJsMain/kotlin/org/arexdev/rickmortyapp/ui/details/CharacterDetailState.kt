package org.arexdev.rickmortyapp.ui.details

import org.arexdev.rickmortyapp.domain.model.CharacterModel
import org.arexdev.rickmortyapp.domain.model.EpisodeModel

data class CharacterDetailState(
    val characterModel: CharacterModel,
    val episodes: List<EpisodeModel>? = null
)