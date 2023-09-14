package me.xditya.ultroid.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CustomBottomBar(navController: NavController) {
    val currentDestination = navController.currentDestination?.route
    BottomAppBar(actions = {
        Spacer(modifier = Modifier.padding(8.dp))
        FloatingActionButton(onClick = {
            if (currentDestination != "home") navController.navigate("home")
        }) {
            Icon(imageVector = Icons.Rounded.Home, contentDescription = "home")
        }
        Spacer(modifier = Modifier.padding(8.dp))
        FloatingActionButton(onClick = {
            if (currentDestination != "all_plugins") navController.navigate("all_plugins")
        }) {
            Icon(imageVector = Icons.Rounded.List, contentDescription = "All plugins")
        }
    }, floatingActionButton = {
        ExtendedFloatingActionButton(text = { Text("Search Plugins") }, icon = {
            Icon(
                Icons.Outlined.Search, contentDescription = "search plugins"
            )
        }, onClick = {
            if (currentDestination != "search") navController.navigate("search")
        })
    })
}

@Preview
@Composable
fun PrevBottomTaskBar() {
    CustomBottomBar(navController = NavController(LocalContext.current))
}