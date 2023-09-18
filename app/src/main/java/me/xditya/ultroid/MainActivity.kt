package me.xditya.ultroid

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import me.xditya.ultroid.helpers.AppData
import me.xditya.ultroid.helpers.LoadPlugins
import me.xditya.ultroid.navigation.Navigation
import me.xditya.ultroid.ui.theme.UltroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UltroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MainApp()
                }
            }
        }
    }
}

@Composable
fun MainApp() {

    // check creds
    val sharedPreferences =
        LocalContext.current.getSharedPreferences("TheUltroidCreds", Context.MODE_PRIVATE)
    val botUsername = sharedPreferences.getString("botUsername", "")
    if (botUsername != "") {
        AppData.botUsername = botUsername!!
    }

    // load plugins
    LoadPlugins(LocalContext.current)

    // load application home-screen
    val nav = rememberNavController()
    Navigation(navController = nav)
}