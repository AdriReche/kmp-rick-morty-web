package org.arexdev.rickmortyapp.di

import org.arexdev.rickmortyapp.data.RepositoryImpl
import org.arexdev.rickmortyapp.data.remote.ApiServiceKtor
import org.arexdev.rickmortyapp.domain.GetCharacterImage
import org.arexdev.rickmortyapp.domain.GetRandomCharacter
import org.arexdev.rickmortyapp.domain.Repository


object Provider {
    val apiService = ApiServiceKtor()
    val repository: Repository = RepositoryImpl(apiService)
    val getRandomCharacter = GetRandomCharacter(repository)

    val getCharacterImage = GetCharacterImage(repository)
}