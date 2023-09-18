package me.xditya.ultroid.helpers

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.json.JSONArray

@Composable
fun LoadPlugins(ctx: Context) {
    // load plugins
    val result = remember {
        mutableStateOf("")
    }
    getAllPlugins(ctx, result)
    val pluginData: JSONArray
    if (!result.value.contains("[")) {
        return
    }
    try {
        pluginData = JSONArray(result.value)
    } catch (e: Exception) {
        Toast.makeText(
            ctx,
            "Failed to parse details..",
            Toast.LENGTH_SHORT
        ).show()
        return
    }
    AppData.plugins = pluginData
}