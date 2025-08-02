package org.arexdev.rickmortyapp.ui.core.navigation

import kotlinx.serialization.Serializable

sealed class Routes(val route: String) {
    data object Home : Routes("home")

    //Bottom Navigation Routes
    data object Episodes : Routes("episodes")
    data object Characters : Routes("characters")
}

@Serializable
data class CharacterDetail(val characterModel: String)