package org.arexdev.rickmortyapp.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.arexdev.rickmortyapp.ui.core.navigation.bottomnavigation.BottomBarItem
import org.arexdev.rickmortyapp.ui.core.navigation.bottomnavigation.NavigationBottomWrapper

@Composable
fun HomeScreen(mainNavController: NavHostController) {
    val items = listOf(BottomBarItem.Episodes(), BottomBarItem.Characters())
    val bottomBarNavController = rememberNavController()
    Scaffold(bottomBar = { BottomNavigationBar(items, bottomBarNavController) }) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            NavigationBottomWrapper(bottomBarNavController, mainNavController)
        }
    }
}

@Composable
fun BottomNavigationBar(items: List<BottomBarItem>, navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = item.icon,
                label = { Text(item.title) },
                alwaysShowLabel = false,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            // Avoid multiple copies of the same destination
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true
            )
        }
    }
}