package org.arexdev.rickmortyapp.ui.home.tabs.characters

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.arexdev.rickmortyapp.data.paginator.Paginator
import org.arexdev.rickmortyapp.domain.GetRandomCharacter
import org.arexdev.rickmortyapp.domain.Repository
import org.arexdev.rickmortyapp.domain.model.CharacterModel

class CharactersViewModel(
    val getRandomCharacter: GetRandomCharacter,
    private val repository: Repository
) : ViewModel() {
    private val _state = MutableStateFlow<CharactersState>(CharactersState())
    val state: StateFlow<CharactersState> = _state


    private val coroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val paginator = Paginator(
        onRequest = { page -> repository.getAllCharacters(page) },
        onError = { error -> _state.update { it.copy(characters = it.characters.copy(error = error.message)) } },
        onSuccess = { newItems, endReached ->
            _state.update {
                it.copy(
                    characters = it.characters.copy(
                        items = it.characters.items + newItems,
                        endReached = endReached
                    )
                )
            }
        }
    )

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

        loadPaginationNext()

    }

    companion object {
        private var cachedCharacter: CharacterModel? = null
    }

    fun loadPaginationNext() {
        coroutineScope.launch {
            _state.update { it.copy(characters = it.characters.copy(loading = true)) }
            paginator.loadNext()
            _state.update { it.copy(characters = it.characters.copy(loading = false)) }
        }
    }
}