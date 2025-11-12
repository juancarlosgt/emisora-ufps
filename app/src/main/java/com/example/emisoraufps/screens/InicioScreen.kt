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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.emisoraufps.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.example.emisoraufps.PlayerViewModel
@Composable
fun InicioScreen(viewModel: PlayerViewModel = viewModel()) {
    val context = LocalContext.current

    // Solo nos aseguramos de que el player esté inicializado
    LaunchedEffect(Unit) {
        viewModel.initializePlayer(context)
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
    }
}

@Preview
@Composable
fun InicioScreenPreview() {
    InicioScreen()
}
