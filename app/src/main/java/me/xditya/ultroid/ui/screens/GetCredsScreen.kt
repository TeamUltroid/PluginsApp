package me.xditya.ultroid.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import me.xditya.ultroid.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetCredsScreen(navController: NavController) {
    var query by remember { mutableStateOf("") }
    val ctx = LocalContext.current
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text(text = "Welcome!") },
        )
    }) {
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(it), content = {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                var logo = R.drawable.ultroid_logo
                if (!isSystemInDarkTheme()) {
                    logo = R.drawable.ultroid_logo_dark
                }
                Image(
                    painter = painterResource(id = logo),
                    contentDescription = "logo",
                    modifier = Modifier
                        .size(300.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.padding(8.dp))
                OutlinedTextField(
                    value = query,
                    onValueChange = { newQuery ->
                        query = newQuery
                    },
                    singleLine = true,
                    maxLines = 1,
                    label = {
                        Text(text = "Telegram Bot Username")
                    }
                )
                Spacer(modifier = Modifier.padding(8.dp))
                if (query.isNotBlank())
                    FloatingActionButton(onClick = {
                        if (!query.endsWith("bot", ignoreCase = true)) {
                            Toast.makeText(ctx, "Invalid bot username", Toast.LENGTH_SHORT).show()
                            query = ""
                        } else if (query.isNotBlank()) {
                            val botUname = query.replace("@", "")
                            val sharedPreferences = ctx.getSharedPreferences(
                                "TheUltroidCreds",
                                android.content.Context.MODE_PRIVATE
                            )
                            sharedPreferences.edit().putString("botUsername", botUname).apply()
                            navController.navigate("home")
                        } else {
                            Toast.makeText(
                                ctx,
                                "Please enter your bot username",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowForward,
                            contentDescription = "next page"
                        )
                    }
            }
        })
    }
}