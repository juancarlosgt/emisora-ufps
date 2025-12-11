package com.example.emisoraufps.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.emisoraufps.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.example.emisoraufps.PlayerViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Calendar

@Composable
fun InicioScreen(
    navController: NavController,
    viewModel: PlayerViewModel = viewModel()
) {
    val context = LocalContext.current

    // Estados para la programación del día
    var programacionHoy by remember { mutableStateOf<List<Programa>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var hoy by remember { mutableStateOf("") }

    // Solo nos aseguramos de que el player esté inicializado
    LaunchedEffect(Unit) {
        viewModel.initializePlayer(context)


    }

    // Función para cargar la programación del día actual
    fun cargarProgramacionDelDia() {
        // Determinar el día actual
        val calendar = Calendar.getInstance()
        val diaSemana = calendar.get(Calendar.DAY_OF_WEEK)
        val diasMap = mapOf(
            Calendar.MONDAY to "Lunes",
            Calendar.TUESDAY to "Martes",
            Calendar.WEDNESDAY to "Miércoles",
            Calendar.THURSDAY to "Jueves",
            Calendar.FRIDAY to "Viernes",
            Calendar.SATURDAY to "Sábado",
            Calendar.SUNDAY to "Domingo"
        )

        val diaActual = diasMap[diaSemana] ?: "Lunes"
        hoy = diaActual

        // Obtener la programación de Firebase
        obtenerProgramasDeFirebase { programasPorDia ->
            programacionHoy = programasPorDia[diaActual] ?: emptyList()
            isLoading = false
        }
    }
    cargarProgramacionDelDia()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFAA1916))
        ) {
            Image(
                painter = painterResource(id = R.drawable.ufps_radio_blanco),
                contentDescription = "UFPS Radio",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .padding(10.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sección de Programación del Día
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            // Título de la sección
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Programación de hoy",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6B0000)
                    )
                    Text(
                        text = hoy,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                // Botón para ver toda la programación
                TextButton(
                    onClick = { navController.navigate("programacion") },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color(0xFFAA1916)
                    )
                ) {
                    Text("Ver toda")
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Ver toda la programación",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                // Mostrar loading mientras se cargan los datos
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF6B0000))
                }
            } else if (programacionHoy.isEmpty()) {
                // Mensaje si no hay programación para hoy
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF9F9F9)
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = "Sin programación",
                            tint = Color.Gray,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "No hay programación para hoy",
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Consulta la programación completa",
                            color = Color(0xFFAA1916),
                            fontSize = 12.sp,
                            modifier = Modifier
                                .clickable { navController.navigate("programacion") }
                                .padding(top = 4.dp)
                        )
                    }
                }
            } else {
                // Mostrar los programas de hoy (limitado a los próximos 3-4 programas)
                val programasParaMostrar = programacionHoy.take(4)

                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    programasParaMostrar.forEach { programa ->
                        ProgramaCard(
                            programa = programa,
                            onClick = {
                                navController.navigate(
                                    "detalle/${programa.nombre}/${programa.horario}"
                                )
                            }
                        )
                    }

                    // Mostrar indicador si hay más programas
                    if (programacionHoy.size > 4) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { navController.navigate("programacion") },
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFF3EFFF)
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Ver ${programacionHoy.size - 4} programas más",
                                    color = Color(0xFF6B0000),
                                    fontWeight = FontWeight.Medium
                                )
                                Icon(
                                    imageVector = Icons.Default.ArrowForward,
                                    contentDescription = "Ver más",
                                    tint = Color(0xFF6B0000),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

// Componente reutilizable para mostrar un programa
@Composable
fun ProgramaCard(
    programa: Programa,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF3EFFF)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            // Ícono del programa
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFFE2D8FF), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = programa.nombre.first().toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF6B0000)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Información del programa
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = programa.nombre,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color(0xFF6B0000),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = programa.horario,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Indicador de que es clickeable
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Ver detalles",
                tint = Color(0xFFAA1916),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

// Función optimizada para obtener solo los datos necesarios
// (Opcional - si quieres optimizar las llamadas a Firebase)
fun obtenerProgramacionDelDia(
    dia: String,
    callback: (List<Programa>) -> Unit
) {
    val database: DatabaseReference = FirebaseDatabase.getInstance()
        .getReference("programas")
        .child(dia)

    database.get().addOnSuccessListener { snapshot ->
        val programas = snapshot.children.mapNotNull {
            it.getValue(Programa::class.java)
        }
        callback(programas)
    }.addOnFailureListener {
        callback(emptyList())
    }
}

