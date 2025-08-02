package org.arexdev.rickmortyapp.ui.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.json.Json
import org.arexdev.rickmortyapp.domain.model.CharacterModel
import org.arexdev.rickmortyapp.ui.details.CharacterDetailScreen
import org.arexdev.rickmortyapp.ui.home.HomeScreen

@Composable
fun NavigationWrapper() {
    val mainNavController = rememberNavController()

    NavHost(navController = mainNavController, startDestination = Routes.Home.route) {
        composable(Routes.Home.route) {
            HomeScreen(mainNavController)
        }

        composable<CharacterDetail> { navBackStackEntry ->
            val characterDetailEncoding: CharacterDetail = navBackStackEntry.toRoute<CharacterDetail>()
            val characterModel = Json.decodeFromString<CharacterModel>(characterDetailEncoding.characterModel)
            CharacterDetailScreen(characterModel)
        }
    }
}