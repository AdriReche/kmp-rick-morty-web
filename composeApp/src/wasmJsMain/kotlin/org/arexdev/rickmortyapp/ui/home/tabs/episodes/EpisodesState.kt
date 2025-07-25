package org.arexdev.rickmortyapp.ui.home.tabs.episodes

import org.arexdev.rickmortyapp.domain.model.EpisodeModel

data class EpisodesState(
    val episodes: EpisodesUiState = EpisodesUiState(),
)

data class EpisodesUiState(
    val items: List<EpisodeModel> = emptyList(),
    val loading: Boolean = false,
    val endReached: Boolean = false,
    val error: String? = null
)