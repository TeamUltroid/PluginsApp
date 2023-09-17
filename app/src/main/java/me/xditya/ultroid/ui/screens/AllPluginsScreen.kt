package me.xditya.ultroid.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import me.xditya.ultroid.components.CustomBottomBar
import me.xditya.ultroid.helpers.AppData
import org.json.JSONArray
import org.json.JSONObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllPluginsScreen(navController: NavController) {
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
            val pluginData: JSONArray = AppData.plugins
            LazyColumn(
                content = {
                    item {
                        Column (
                            modifier = Modifier.padding(8.dp),
                        ){
                            Text(
                                text = "${pluginData.length()} plugins available!",
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }

                    }
                    items(pluginData.length()) { index ->
                        val parsedData = pluginData.getJSONObject(index)
                        val details = JSONObject(parsedData.getString("details"))
                        val desc: String = try {
                            (details.getString("description") + " • ")
                        } catch (e: Exception) {
                            ""
                        }
                        val cmds: String = try {
                            " • ${JSONObject(details.getString("cmds")).length()} commands."
                        } catch (e: Exception) {
                            ""
                        }
                        val subText =
                            desc + ("v" + (details.getString("version")
                                ?: "1.0")) + cmds
                        ListItem(
                            leadingContent = {
                                Icon(
                                    imageVector = Icons.Rounded.Star,
                                    contentDescription = "leading content"
                                )
                            },
                            headlineText = {
                                Text(text = parsedData.getString("name"))
                            },
                            supportingText = {
                                Text(text = subText)
                            },
                            trailingContent = {
                                Icon(
                                    imageVector = Icons.Rounded.KeyboardArrowRight,
                                    contentDescription = "click arrow"
                                )
                            },
                            modifier = Modifier.clickable {
                                navController.navigate("plugin/${parsedData.getString("name")}")
                            }
                        )
                        Divider()
                    }
                })
        }
    }
}