package me.xditya.ultroid

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import me.xditya.ultroid.helpers.AppData
import me.xditya.ultroid.helpers.getAllPlugins
import me.xditya.ultroid.navigation.Navigation
import me.xditya.ultroid.ui.theme.UltroidTheme
import org.json.JSONArray

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UltroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp()
                }
            }
        }
    }
}

@Composable
fun MainApp() {
    val result = remember {
        mutableStateOf("")
    }
    getAllPlugins(LocalContext.current, result)

    val pluginData:JSONArray
    if (!result.value.contains("[")) {
        return
    }
    try {
        pluginData = JSONArray(result.value)
    } catch (e: Exception) {
        Toast.makeText(
            LocalContext.current,
            "Failed to parse details..",
            Toast.LENGTH_SHORT
        ).show()
        return
    }
    AppData.plugins = pluginData

    val nav = rememberNavController()
    Navigation(navController = nav)
}