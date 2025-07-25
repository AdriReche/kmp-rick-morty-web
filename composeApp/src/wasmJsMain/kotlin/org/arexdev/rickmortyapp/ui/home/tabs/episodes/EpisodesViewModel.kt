package org.arexdev.rickmortyapp.ui.home.tabs.episodes

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.arexdev.rickmortyapp.data.paginator.Paginator
import org.arexdev.rickmortyapp.domain.Repository

class EpisodesViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _state = MutableStateFlow<EpisodesState>(EpisodesState())
    val state: StateFlow<EpisodesState> = _state


    private val coroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val paginator = Paginator(
        onRequest = { page -> repository.getAllEpisodes(page) },
        onError = { error -> _state.update { it.copy(episodes = it.episodes.copy(error = error.message)) } },
        onSuccess = { newItems, endReached ->
            _state.update {
                it.copy(
                    episodes = it.episodes.copy(
                        items = it.episodes.items + newItems,
                        endReached = endReached
                    )
                )
            }
        }
    )

    init {
        loadPaginationNext()
    }

    fun loadPaginationNext() {
        coroutineScope.launch {
            _state.update { it.copy(episodes = it.episodes.copy(loading = true)) }
            paginator.loadNext()
            _state.update { it.copy(episodes = it.episodes.copy(loading = false)) }
        }
    }

    fun onPlaySelected(url: String) {
        _state.update { state -> state.copy(playVideo = url) }
    }

    fun onCloseVideo() {
        println("Closing video")
        _state.update { state -> state.copy(playVideo = "") }
    }
}