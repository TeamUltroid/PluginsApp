package me.xditya.ultroid.ui.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import org.json.JSONObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PluginInfoScreen(
    navController: NavController, name: String
) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text(text = "Plugin Info") },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "back")
                }
            },
        )
    }, bottomBar = {
        CustomBottomBar(navController = navController)
    }) {
        Surface(modifier = Modifier.padding(it)) {
            var pluginData = JSONObject()
            for (i in 0 until AppData.plugins.length()) {
                val plugin = AppData.plugins.getJSONObject(i)
                if (plugin.getString("name") == name) {
                    pluginData = plugin
                    break
                }
            }
            val details = pluginData.getJSONObject("details")
            val description = try {
                details.getString("description")
            } catch (e: Exception) {
                ""
            }
            val version = try {
                details.getString("version")
            } catch (e: Exception) {
                ""
            }
            val isInline = try {
                details.getBoolean("inline")
            } catch (e: Exception) {
                false
            }
            val inline: String = if (isInline) {
                "✅"
            } else {
                "❌"
            }
            val cmds = try {
                details.getJSONObject("cmds")
            } catch (e: Exception) {
                JSONObject()
            }
            LazyColumn(modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(), content = {
                item {
                    ElevatedCard(
                        modifier = Modifier.fillMaxSize(),
                        content = {
                            Text(
                                text = name,
                                style = MaterialTheme.typography.headlineLarge,
                                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                            )
                            Text(
                                text = "• v$version",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                            )
                            Text(
                                text = "• $description",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                            )
                            Text(
                                text = "• Inline - $inline",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(16.dp)
                            )
                            Text(
                                text = "• Commands:",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                            )
                            for (i in cmds.keys()) {
                                Text(
                                    text = "\uD83D\uDC49\t $i: ${cmds.getString(i)}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(start = 16.dp, top = 2.dp)
                                )
                            }
                            Spacer(modifier = Modifier.padding(16.dp))
                        },
                    )
                }
            })
        }
    }
}
