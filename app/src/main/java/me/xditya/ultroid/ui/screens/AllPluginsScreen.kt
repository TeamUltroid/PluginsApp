package me.xditya.ultroid.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import me.xditya.ultroid.ui.CustomBottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllPlugins(navController: NavController) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text(text = "All plugins") },
        )
    },
        bottomBar = {
            CustomBottomBar(navController = navController)
        }
    ) {
        Surface(modifier = Modifier.padding(it)) {

        }
    }
}