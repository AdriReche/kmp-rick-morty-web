package org.arexdev.rickmortyapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.arexdev.rickmortyapp.ui.core.BackgroundPrimaryColor
import org.arexdev.rickmortyapp.ui.core.BackgroundSecondaryColor
import org.arexdev.rickmortyapp.ui.core.BackgroundTertiaryColor
import org.arexdev.rickmortyapp.ui.core.DefaultTextColor
import org.arexdev.rickmortyapp.ui.core.Green
import org.arexdev.rickmortyapp.ui.core.navigation.bottomnavigation.BottomBarItem
import org.arexdev.rickmortyapp.ui.core.navigation.bottomnavigation.NavigationBottomWrapper
import org.jetbrains.compose.resources.painterResource
import rickmortyweb.composeapp.generated.resources.Res
import rickmortyweb.composeapp.generated.resources.ricktoolbar

@Composable
fun HomeScreen(mainNavController: NavHostController) {
    val items = listOf(BottomBarItem.Episodes(), BottomBarItem.Characters())
    val bottomBarNavController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(items, bottomBarNavController) },
        topBar = { TopBar() }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            NavigationBottomWrapper(bottomBarNavController, mainNavController)
        }
    }
}

@Composable
fun TopBar() {
    Box(modifier = Modifier.fillMaxWidth().background(BackgroundPrimaryColor), contentAlignment = Alignment.TopCenter) {
        Image(
            painter = painterResource(Res.drawable.ricktoolbar),
            contentDescription = null,
            modifier = Modifier.padding(start = 16.dp, top = 32.dp, bottom = 8.dp),
        )
    }
}

@Composable
fun BottomNavigationBar(items: List<BottomBarItem>, navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(containerColor = BackgroundSecondaryColor, contentColor = Green) {
        items.forEach { item ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Green,
                    selectedIconColor = BackgroundTertiaryColor,
                    unselectedIconColor = Green,
                ),
                icon = item.icon,
                label = { Text(item.title, color = DefaultTextColor) },
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