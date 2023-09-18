package me.xditya.ultroid.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import compose.icons.SimpleIcons
import compose.icons.simpleicons.Github
import compose.icons.simpleicons.Telegram
import me.xditya.ultroid.R
import me.xditya.ultroid.components.CustomBottomBar
import me.xditya.ultroid.helpers.AppData

fun linkToWebpage(context: Context, uri: String) {
    val openURL = Intent(Intent.ACTION_VIEW)
    openURL.data = Uri.parse(uri)
    startActivity(context, openURL, null)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val ctx = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var showDialogCreds by remember { mutableStateOf(false) }
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = { Text(text = "Ultroid") }, navigationIcon = {
            IconButton(onClick = {
                showDialog = true
            }) {
                Icon(
                    imageVector = Icons.Rounded.Refresh, contentDescription = "clear creds"
                )
            }
        },
            actions = {
                IconButton(onClick = {
                    showDialogCreds = true
                }) {
                    Icon(
                        imageVector = Icons.Rounded.Info, contentDescription = "show creds"
                    )
                }
            })
        if (showDialog) {
            PopupDialog(onDismiss = {
                showDialog = false
            }, onAccept = {
                showDialog = false
                AppData.botUsername = ""
                val savedPrefs = ctx.getSharedPreferences("TheUltroidCreds", Context.MODE_PRIVATE)
                savedPrefs.edit().putString("botUsername", "").apply()
                navController.navigate("get_creds_page")
            })
        }
        if (showDialogCreds) {
            PopupDialogCreds(onDismiss = {
                showDialogCreds = false
            })
        }
    }, bottomBar = {
        CustomBottomBar(navController = navController)
    }) {
        Surface(modifier = Modifier.padding(it)) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                var logo = R.drawable.ultroid_logo
                if (!isSystemInDarkTheme()) {
                    logo = R.drawable.ultroid_logo_dark
                }
                Image(
                    painter = painterResource(id = logo),
                    contentDescription = "logo",
                    modifier = Modifier
                        .size(400.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "Pluggable Telegram UserBot",
                    style = MaterialTheme.typography.headlineSmall.copy(fontFamily = FontFamily.Serif),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.padding(16.dp))
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                ) {
                    IconButton(onClick = {
                        linkToWebpage(ctx, "https://github.com/TeamUltroid")
                    }) {
                        Icon(
                            imageVector = SimpleIcons.Github,
                            contentDescription = "github",
                            Modifier.size(50.dp)
                        )
                    }
                    Spacer(modifier = Modifier.padding(16.dp))
                    IconButton(onClick = {
                        linkToWebpage(ctx, "https://t.me/TeamUltroid")
                    }) {
                        Icon(
                            imageVector = SimpleIcons.Telegram,
                            contentDescription = "tg",
                            Modifier.size(50.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PopupDialog(
    onDismiss: () -> Unit, onAccept: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        AlertDialog(onDismissRequest = { onDismiss() },
            title = { Text(text = "Reset and reload app?") },
            text = { Text(text = "This would clear your saved credentials, like bot token and reload all plugins!") },
            confirmButton = {
                Button(
                    onClick = { onAccept() }, modifier = Modifier.padding(8.dp)
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = { onDismiss() }, modifier = Modifier.padding(8.dp)
                ) {
                    Text("Cancel")
                }
            })
    }
}

@Composable
fun PopupDialogCreds(
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        AlertDialog(onDismissRequest = { onDismiss() },
            title = { Text(text = "Credentials") },
            text = { Text(text = "Bot username: @${AppData.botUsername}") },
            confirmButton = {
                Button(
                    onClick = { onDismiss() }, modifier = Modifier.padding(8.dp)
                ) {
                    Text("Close")
                }
            })
    }
}

@Preview
@Composable
fun PrevHomeScreen() {
    HomeScreen(navController = NavController(LocalContext.current))
}