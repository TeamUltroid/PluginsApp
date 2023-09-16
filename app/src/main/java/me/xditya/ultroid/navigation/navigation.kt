package me.xditya.ultroid.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import me.xditya.ultroid.ui.screens.AllPluginsScreen
import me.xditya.ultroid.ui.screens.HomeScreen
import me.xditya.ultroid.ui.screens.PluginInfoScreen
import me.xditya.ultroid.ui.screens.SearchScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
        }
        composable("search") {
            SearchScreen(navController)
        }
        composable("all_plugins") {
            AllPluginsScreen(navController)
        }
        composable("plugin/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name")
            PluginInfoScreen(navController = navController, name = name!!)
        }
    }
}