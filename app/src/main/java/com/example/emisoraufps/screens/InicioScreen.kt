package com.example.emisoraufps.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.emisoraufps.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem

@Composable
fun InicioScreen() {

    var isPlaying by remember { mutableStateOf(false) }

    // Crear el ExoPlayer
    val context = LocalContext.current
    val player = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri("https://apps.ufps.edu.co/emisoraufps")
            setMediaItem(mediaItem)
            prepare()
        }
    }

    // Cuando isPlaying cambie, pausamos o comenzamos el stream
    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            player.play() // Reproducir el stream
        } else {
            player.pause() // Detener el stream
        }
    }


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFAA1916)) 
                .padding(10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ufps_radio_blanco),
                contentDescription = "UFPS Radio",
                modifier = Modifier
                    .height(140.dp)
                    .align(Alignment.CenterStart)
            )
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = if (isPlaying) painterResource(id = R.drawable.pause_button) else painterResource(id = R.drawable.play_button),
                    contentDescription = "Play Button",
                    modifier = Modifier
                        .size(48.dp)
                        .clickable {
                            isPlaying = !isPlaying
                        },
                    tint = Color.Unspecified

                )   
            }
        }

    }

    DisposableEffect(context) {
        onDispose {
            player.release()
        }
    }
}

@Preview
@Composable
fun InicioScreenPreview() {
    InicioScreen()
}
