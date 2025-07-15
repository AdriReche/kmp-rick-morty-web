package org.arexdev.rickmortyapp.di

import org.arexdev.rickmortyapp.data.RepositoryImpl
import org.arexdev.rickmortyapp.data.localstorage.LocalStoreService
import org.arexdev.rickmortyapp.data.remote.ApiServiceKtor
import org.arexdev.rickmortyapp.domain.GetCharacterImage
import org.arexdev.rickmortyapp.domain.GetRandomCharacter
import org.arexdev.rickmortyapp.domain.Repository
import org.arexdev.rickmortyapp.ui.home.tabs.characters.CharactersViewModel
import org.arexdev.rickmortyapp.ui.home.tabs.episodes.EpisodesViewModel


object Provider {
    val apiService = ApiServiceKtor()
    val localStoreService = LocalStoreService()
    val repository: Repository = RepositoryImpl(apiService, localStoreService)
    val getRandomCharacter = GetRandomCharacter(repository)
    val getCharacterImage = GetCharacterImage(repository)
    val episodesViewModel: EpisodesViewModel by lazy { EpisodesViewModel(repository) }
    val charactersViewModel: CharactersViewModel by lazy { CharactersViewModel(getRandomCharacter, repository) }
}