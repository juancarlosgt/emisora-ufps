package com.example.emisoraufps.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.emisoraufps.PlayerViewModel
import com.example.emisoraufps.R

@Composable
fun PersistentMiniPlayer(
    viewModel: PlayerViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Inicializar el player una vez
    LaunchedEffect(Unit) {
        viewModel.initializePlayer(context)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Información de la estación
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "UFPS Radio",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (viewModel.isPlaying) "En vivo" else "Pausado",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            // Controles
            Icon(
                painter = if (viewModel.isPlaying)
                    painterResource(id = R.drawable.pause_button)
                else
                    painterResource(id = R.drawable.play_button),
                contentDescription = if (viewModel.isPlaying) "Pausar" else "Reproducir",
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        viewModel.togglePlayback()
                    },
                tint = Color.Unspecified
            )
        }
    }
}