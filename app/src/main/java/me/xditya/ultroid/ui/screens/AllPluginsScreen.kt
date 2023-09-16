package me.xditya.ultroid.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import me.xditya.ultroid.components.CustomBottomBar
import me.xditya.ultroid.helpers.getAllPlugins
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
            val result = remember {
                mutableStateOf("")
            }
            val pluginData: JSONArray
            getAllPlugins(LocalContext.current, result)
            if (!result.value.contains("[")) {
                Toast.makeText(
                    LocalContext.current,
                    "Failed to fetch details..",
                    Toast.LENGTH_SHORT
                ).show()
                return@Surface
            }
            try {
                pluginData = JSONArray(result.value)
            } catch (e: Exception) {
                Toast.makeText(
                    LocalContext.current,
                    "Failed to parse details..",
                    Toast.LENGTH_SHORT
                ).show()
                return@Surface
            }
            LazyColumn(
                content = {
                    items(pluginData.length()) { index ->
                        val parsedData = pluginData.getJSONObject(index)
                        val details = JSONObject(parsedData.getString("details"))
                        val desc: String = try {
                            (details.getString("description") + " • ")
                        } catch (e: Exception) {
                            ""
                        }
                        val cmds: String = try {
                            " • ${details.getString("cmds").length} commands."
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