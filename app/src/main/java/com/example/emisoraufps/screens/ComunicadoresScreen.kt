package com.example.emisoraufps.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

// Modelo de datos para comunicadores
data class Comunicador(
    val nombre: String = "",
    val programa: String = "",
    val inicial: String = "",
    val biografia: String = "",
    val rol: String = "",
    val color: Color = Color(0xFF8B7ABA)
)

// Datos de ejemplo
// private val comunicadoresList = listOf(
//     Comunicador("Ing Matias", "bailando sin cesar", "A", "Biografia de Ing Matias", "Rol de Ing Matias"),
//     Comunicador("Ing Adarme", "Data Structures", "A", "Biografia de Ing Adarme", "Rol de Ing Adarme"),
//     Comunicador("Ing Pilar Rojas", "Apilando canciones", "A", "Biografia de Ing Pilar Rojas", "Rol de Ing Pilar Rojas"),
//     Comunicador("Diany", "canciones de los 80", "A", "Biografia de Diany", "Rol de Diany")
// )

fun obtenerComunicadoresDeFirebase(callback: (List<Comunicador>) -> Unit) {
    val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("comunicadores")

    database.get().addOnSuccessListener { snapshot ->
        val comunicadores = snapshot.children.mapNotNull { dataSnapshot ->
            val nombre = dataSnapshot.child("nombre").getValue(String::class.java) ?: ""
            val biografia = dataSnapshot.child("biografia").getValue(String::class.java) ?: ""
            val rol = dataSnapshot.child("rol").getValue(String::class.java) ?: ""
            val inicial = nombre.firstOrNull()?.uppercase() ?: "A"

            if (nombre.isNotEmpty()) {
                Comunicador(
                    nombre = nombre,
                    programa = biografia,
                    inicial = inicial,
                    biografia = biografia,
                    rol = rol
                )
            } else {
                null
            }
        }
        callback(comunicadores)
    }.addOnFailureListener {
        // Manejar error
        callback(emptyList())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComunicadoresScreen(navController: NavController) {
    var comunicadores by remember { mutableStateOf<List<Comunicador>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        obtenerComunicadoresDeFirebase { comunicadoresObtenidos ->
            comunicadores = comunicadoresObtenidos
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color(0xFFD32F2F)),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Logo UFPS (simplificado)
                    Column {
                        Text(
                            "95.2",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            ),
                            fontSize = 10.sp
                        )
                        Text(
                            "UFPS",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                        Text(
                            "Radio",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color.White
                            ),
                            fontSize = 10.sp
                        )
                    }

                    // Play button
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "▶",
                            color = Color(0xFFD32F2F),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            // Título
            Text(
                "Comunicadores",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 24.dp, bottom = 16.dp)
            )

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFFD32F2F))
                }
            } else {
                // Lista de comunicadores
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (comunicadores.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No hay comunicadores disponibles",
                                    color = Color.Gray
                                )
                            }
                        }
                    } else {
                        items(comunicadores) { comunicador ->
                            ComunicadorCard(comunicador, navController)
                        }
                    }
                }
            }

            // Botones de navegación anterior/siguiente
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFE8D5F2))
                        .clickable { /* TODO: ir anterior */ },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.SkipPrevious,
                        contentDescription = "Anterior",
                        tint = Color(0xFF7C5BA3),
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFE8D5F2))
                        .clickable { /* TODO: ir siguiente */ },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.SkipNext,
                        contentDescription = "Siguiente",
                        tint = Color(0xFF7C5BA3),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ComunicadorCard(comunicador: Comunicador, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("comunicador/${comunicador.nombre}")
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF3EFFF)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Avatar con inicial
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFCFBAE3)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    comunicador.inicial,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF7C5BA3)
                    )
                )
            }

            // Información del comunicador
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    comunicador.nombre,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )

            }

            // Iconos de acción (simplificados con símbolos)
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.padding(end = 8.dp)
            ) {
            }
        }
    }
}
