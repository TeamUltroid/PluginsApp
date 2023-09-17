package me.xditya.ultroid.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import me.xditya.ultroid.components.CustomBottomBar
import me.xditya.ultroid.helpers.AppData
import org.json.JSONArray
import org.json.JSONObject

@Composable
fun SegmentedButton(
    options: List<String>, selectedOption: String, onOptionSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        options.forEach { option ->
            val isSelected = option == selectedOption

            Button(
                onClick = {
                    if (!isSelected) {
                        onOptionSelected(option)
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) Color.Gray else Color.White,
                    contentColor = if (isSelected) Color.White else Color.Black
                )
            ) {
                Text(
                    text = option, style = MaterialTheme.typography.bodyMedium, maxLines = 1
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AllPluginsScreen(navController: NavController) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text(text = "All plugins") },
        )
    }, bottomBar = {
        CustomBottomBar(navController = navController)
    }) {
        var selectedOption by remember { mutableStateOf(AppData.currentOption) }
        val options = listOf("all", "inline", "commands")

        Surface(modifier = Modifier.padding(it)) {
            val pluginData: JSONArray = AppData.plugins
            val filteredItems = remember(pluginData, selectedOption) {
                filterPlugins(pluginData, selectedOption)
            }
            LazyColumn(content = {
                stickyHeader {
                    Card {
                        Column(
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text(
                                text = "${filteredItems.size} plugins available!",
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                        SegmentedButton(options = options,
                            selectedOption = selectedOption,
                            onOptionSelected = { selectedOpt ->
                                selectedOption = selectedOpt
                                AppData.currentOption = selectedOpt
                            })
                    }
                }
                items(filteredItems.size) { index ->
                    val parsedData = filteredItems[index]
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
                    val subText = desc + ("v" + (details.getString("version") ?: "1.0")) + cmds
                    ListItem(leadingContent = {
                        Icon(
                            imageVector = Icons.Rounded.Star, contentDescription = "leading content"
                        )
                    }, headlineText = {
                        Text(text = parsedData.getString("name"))
                    }, supportingText = {
                        Text(text = subText)
                    }, trailingContent = {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowRight,
                            contentDescription = "click arrow"
                        )
                    }, modifier = Modifier.clickable {
                        navController.navigate("plugin/${parsedData.getString("name")}")
                    })
                    Divider()
                }
            })
        }
    }
}

private fun filterPlugins(pluginData: JSONArray, selectedOption: String): List<JSONObject> {
    return when (selectedOption) {
        "all" -> {
            (0 until pluginData.length()).map { pluginData.getJSONObject(it) }
        }

        "inline" -> {
            (0 until pluginData.length()).map { pluginData.getJSONObject(it) }.filter { plugin ->
                val details = JSONObject(plugin.getString("details"))
                try {
                    details.getBoolean("inline")
                } catch (e: Exception) {
                    false
                }
            }
        }

        "commands" -> {
            (0 until pluginData.length()).map { pluginData.getJSONObject(it) }.filter { plugin ->
                val details = JSONObject(plugin.getString("details"))
                try {
                    details.getBoolean("inline")
                } catch (e: Exception) {
                    true
                }
            }
        }

        else -> emptyList()
    }
}