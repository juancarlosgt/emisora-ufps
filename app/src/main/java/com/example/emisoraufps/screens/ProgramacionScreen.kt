package com.example.emisoraufps.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.clickable

// Modelo de datos
data class Programa(
    val nombre: String,
    val horario: String
)

// Datos de ejemplo
private val programasPorDia = mapOf(
    "Lunes" to listOf(
        Programa("Artistas UFPS Radio", "12:00 AM - 5:00 AM"),
        Programa("Sonidos Macondianos", "6:00 AM - 8:00 AM"),
        Programa("Magazín Siente La U", "8:00 AM - 9:00 AM")
    ),
    "Martes" to listOf(
        Programa("Cultura al Aire", "7:00 AM - 9:00 AM"),
        Programa("Conexión Deportiva", "10:00 AM - 11:00 AM")
    ),
    "Miércoles" to listOf(
        Programa("Noticias UFPS", "6:00 AM - 7:00 AM")
    ),
    "Jueves" to listOf(
        Programa("Voces del Campus", "8:00 AM - 9:00 AM")
    ),
    "Viernes" to listOf(
        Programa("Fin de Semana UFPS", "9:00 AM - 10:00 AM")
    ),
    "Sábado" to listOf(
        Programa("Sábados Musicales", "10:00 AM - 12:00 PM")
    ),
    "Domingo" to listOf(
        Programa("Domingos Culturales", "8:00 AM - 10:00 AM")
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgramacionScreen(navController: NavController) {
    var diaSeleccionado by remember { mutableStateOf("Lunes") }

    val dias = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")
    val programas = programasPorDia[diaSeleccionado] ?: emptyList()


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFAA1916))
                .padding(vertical = 16.dp)
        ) {
            Text(
                text = "Programación",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                dias.forEach { dia ->
                    val isSelected = dia == diaSeleccionado
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = if (isSelected) Color(0xFF6B0000) else Color(0xFFFFC9C9),
                        modifier = Modifier
                            .clickable { diaSeleccionado = dia }
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = dia,
                            color = if (isSelected) Color.White else Color.Black,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Lista de programas
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(programas) { programa ->
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable {
                                navController.navigate(
                                    "detalle/${programa.nombre}/${programa.horario}"
                                )
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFF3EFFF)
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(Color(0xFFE2D8FF), shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = programa.nombre.first().toString(),
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF6B0000)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = programa.nombre,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp,
                                    color = Color(0xFF6B0000)

                                )
                                Text(
                                    text = programa.horario,
                                    fontSize = 13.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
