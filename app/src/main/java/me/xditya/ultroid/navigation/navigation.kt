package me.xditya.ultroid.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import me.xditya.ultroid.helpers.AppData
import me.xditya.ultroid.helpers.LoadPlugins
import me.xditya.ultroid.ui.screens.AllPluginsScreen
import me.xditya.ultroid.ui.screens.GetCredsScreen
import me.xditya.ultroid.ui.screens.HomeScreen
import me.xditya.ultroid.ui.screens.PluginInfoScreen
import me.xditya.ultroid.ui.screens.SearchScreen

@Composable
fun Navigation(navController: NavHostController) {
    var startDestination = "home"
    if (AppData.botUsername == "")
        startDestination = "get_creds_page"
    NavHost(navController = navController, startDestination = startDestination) {
        composable("home") {
            HomeScreen(navController)
        }
        composable("get_creds_page") {
            LoadPlugins(ctx = LocalContext.current)
            GetCredsScreen(navController = navController)
        }
        composable(route = "search") {
            SearchScreen(navController)
        }
        composable("all_plugins") {
            AllPluginsScreen(navController)
        }
        composable(
            "plugin/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name")
            PluginInfoScreen(navController = navController, name = name!!)
        }
    }
}