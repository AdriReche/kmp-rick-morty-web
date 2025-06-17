package org.arexdev.rickmortyapp.ui.core.navigation

sealed class Routes(val route: String) {
    data object Home : Routes("home")


    //Bottom Navigation Routes
    data object Episodes : Routes("episodes")
    data object Characters : Routes("characters")
}