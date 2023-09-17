package me.xditya.ultroid.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import me.xditya.ultroid.components.CustomBottomBar
import me.xditya.ultroid.helpers.AppData
import org.json.JSONObject

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier, query: String, onQueryChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        value = query,
        onValueChange = { newQuery ->
            onQueryChange(newQuery)
        },
        textStyle = TextStyle(
            fontSize = 18.sp,
        ),
        singleLine = true,
        label = {
            Text(text = "Enter a plugin name")
        },
        leadingIcon = {
            Icon(imageVector = Icons.Rounded.Search, contentDescription = "search icon")
        },
        trailingIcon = {
            IconButton(onClick = {
                onQueryChange("")
            }) {
                Icon(
                    imageVector = Icons.Rounded.Clear,
                    contentDescription = "search icon",
                    Modifier.size(20.dp)
                )
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {
    var query by remember { mutableStateOf("") }
    val matches = remember { mutableStateListOf<JSONObject>() }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text(text = "Plugins Search") },
        )
    }, bottomBar = {
        CustomBottomBar(navController = navController)
    }) {
        Surface(modifier = Modifier.padding(it)) {
            Column(modifier = Modifier.padding(16.dp)) {
                SearchBar(query = query, onQueryChange = { newQuery ->
                    query = newQuery
                    updateMatches(query, matches)
                    if (query.isEmpty()) {
                        matches.clear()
                    }
                })
                LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
                    items(matches) { jsonObject ->
                        val name = jsonObject.getString("name")
                        val details = JSONObject(jsonObject.getString("details"))
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
                            headlineText = {
                                Text(text = name, fontSize = 18.sp)
                            },
                            supportingText = {
                                Text(text = subText)
                            },
                            modifier = Modifier.clickable {
                                navController.navigate("plugin/${name}")
                            },
                            leadingContent = {
                                Icon(
                                    imageVector = Icons.Rounded.Star,
                                    contentDescription = "leading content"
                                )
                            },
                            trailingContent = {
                                Icon(
                                    imageVector = Icons.Rounded.KeyboardArrowRight,
                                    contentDescription = "click arrow"
                                )
                            },
                        )
                        Divider()
                    }
                }
            }
        }
    }
}

private fun updateMatches(query: String, matches: MutableList<JSONObject>) {
    matches.clear()
    for (i in 0 until AppData.plugins.length()) {
        val jsonObject = AppData.plugins.getJSONObject(i)
        val name = jsonObject.getString("name")
        val cmds: JSONObject = try {
            JSONObject(jsonObject.getJSONObject("details").getString("cmds"))
        } catch (e: Exception) {
            JSONObject()
        }
        if (name.contains(query, ignoreCase = true) && !matches.contains(jsonObject)) {
            matches.add(jsonObject)
        }
        for (cmd in cmds.keys()) {
            if (cmd.contains(query, ignoreCase = true) && !matches.contains(jsonObject)) {
                matches.add(jsonObject)
                break
            }
        }
    }
}
