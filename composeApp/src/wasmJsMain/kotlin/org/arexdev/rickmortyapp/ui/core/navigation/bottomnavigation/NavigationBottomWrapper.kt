package org.arexdev.rickmortyapp.ui.core.navigation.bottomnavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.arexdev.rickmortyapp.ui.core.navigation.Routes
import org.arexdev.rickmortyapp.ui.home.tabs.characters.CharactersScreen
import org.arexdev.rickmortyapp.ui.home.tabs.episodes.EpisodesScreen


@Composable
fun NavigationBottomWrapper(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Routes.Episodes.route) {
        composable(Routes.Episodes.route) {
            EpisodesScreen()
        }
        composable(Routes.Characters.route) {
            CharactersScreen()
        }

    }
}