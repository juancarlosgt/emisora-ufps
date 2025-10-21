package com.example.emisoraufps.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.emisoraufps.R

@Composable
fun InicioScreen() {
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
