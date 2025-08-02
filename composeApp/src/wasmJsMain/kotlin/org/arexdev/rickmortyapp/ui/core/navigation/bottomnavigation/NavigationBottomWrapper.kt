package org.arexdev.rickmortyapp.ui.core.navigation.bottomnavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.arexdev.rickmortyapp.ui.core.navigation.CharacterDetail
import org.arexdev.rickmortyapp.ui.core.navigation.Routes
import org.arexdev.rickmortyapp.ui.home.tabs.characters.CharactersScreen
import org.arexdev.rickmortyapp.ui.home.tabs.episodes.EpisodesScreen


@Composable
fun NavigationBottomWrapper(bottomBarNavController: NavHostController, mainNavController: NavHostController) {

    NavHost(navController = bottomBarNavController, startDestination = Routes.Episodes.route) {
        composable(Routes.Episodes.route) {
            EpisodesScreen()
        }
        composable(Routes.Characters.route) {
            CharactersScreen(
                navigateToDetail = { characterModel ->
                    val encode: String = Json.encodeToString(characterModel)
                    mainNavController.navigate(CharacterDetail(encode))
                }
            )
        }

    }
}
