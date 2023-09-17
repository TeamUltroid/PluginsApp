package me.xditya.ultroid.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import compose.icons.SimpleIcons
import compose.icons.simpleicons.Github
import compose.icons.simpleicons.Telegram
import me.xditya.ultroid.R
import me.xditya.ultroid.components.CustomBottomBar

fun linkToWebpage(context: Context, uri: String) {
    val openURL = Intent(Intent.ACTION_VIEW)
    openURL.data = Uri.parse(uri)
    startActivity(context, openURL, null)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val ctx = LocalContext.current
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text(text = "Ultroid") },
        )
    },
        bottomBar = {
            CustomBottomBar(navController = navController)
        }
    ) {
        Surface(modifier = Modifier.padding(it)) {
            Column(modifier = Modifier.padding(16.dp)) {
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
                    })
                    {
                        Icon(
                            imageVector = SimpleIcons.Github,
                            contentDescription = "github",
                            Modifier.size(50.dp)
                        )
                    }
                    Spacer(modifier = Modifier.padding(16.dp))
                    IconButton(onClick = {
                        linkToWebpage(ctx, "https://t.me/TeamUltroid")
                    })
                    {
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

@Preview
@Composable
fun PrevHomeScreen() {
    HomeScreen(navController = NavController(LocalContext.current))
}