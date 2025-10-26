package com.example.emisoraufps.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.emisoraufps.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleProgramaScreen(
    navController: NavController,
    nombre: String,
    horario: String,
    descripcion: String = "Es el colectivo sonoro que da vida al sentir musical de nuestra emisora..."
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDFDFD))
    ) {

        // Barra superior con botón de retroceso
        TopAppBar(
            title = {
                Text(
                    text = nombre,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF6B0000),
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White
            )
        )

        // Imagen representativa del programa
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(16.dp)
                .background(Color(0xFFEDE7F6), shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), // Reemplázalo por tu imagen real
                contentDescription = "Imagen del programa",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(100.dp)
            )
        }

        // Información del programa
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = nombre,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
            )

            Text(
                text = horario,
                fontSize = 15.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = descripcion,
                fontSize = 15.sp,
                textAlign = TextAlign.Justify,
                lineHeight = 20.sp
            )
        }
    }
}
