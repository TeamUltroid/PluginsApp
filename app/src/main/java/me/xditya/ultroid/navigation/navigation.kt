package me.xditya.ultroid.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import me.xditya.ultroid.ui.screens.AllPlugins
import me.xditya.ultroid.ui.screens.HomeScreen
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
            AllPlugins(navController)
        }
//        composable("details_page/{id}",
//            arguments = listOf(navArgument("id") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val id = backStackEntry.arguments?.getString("id")
//            DetailsPage(navController = navController, id = id!!)
//        }
    }
}