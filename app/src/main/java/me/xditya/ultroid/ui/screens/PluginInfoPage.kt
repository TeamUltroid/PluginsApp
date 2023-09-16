package me.xditya.ultroid.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import me.xditya.ultroid.components.CustomBottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PluginInfoScreen(
    navController: NavController,
    name: String
) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text(text = name) },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "back")
                }
            },
        )
    },
        bottomBar = {
            CustomBottomBar(navController = navController)
        }
    ) {
        Surface(modifier = Modifier.padding(it)) {
            Text(text = "#TODO")
        }
    }
}