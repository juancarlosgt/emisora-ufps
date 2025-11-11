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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

data class Programa(
    val nombre: String = "",
    val horario: String = ""
)

// Obtener los datos de Firebase
// Opción 1: Usando callback
// Opción 1: Usando callback
fun obtenerProgramasDeFirebase(callback: (Map<String, List<Programa>>) -> Unit) {
    val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("programas")

    database.get().addOnSuccessListener { snapshot ->
        val programas = mutableMapOf<String, List<Programa>>()
        snapshot.children.forEach { daySnapshot ->
            val dia = daySnapshot.key ?: return@forEach
            val listaProgramas = daySnapshot.children.mapNotNull {
                it.getValue(Programa::class.java)
            }
            programas[dia] = listaProgramas
        }
        callback(programas)
    }.addOnFailureListener {
        // Manejar error
        callback(emptyMap())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgramacionScreen(navController: NavController) {
    var diaSeleccionado by remember { mutableStateOf("Lunes") }
    var programasPorDia by remember { mutableStateOf<Map<String, List<Programa>>>(emptyMap()) }
    var isLoading by remember { mutableStateOf(true) }

    val dias = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")
    val programas = programasPorDia[diaSeleccionado] ?: emptyList()

    // Cargar los datos de Firebase
    LaunchedEffect(Unit) {
        obtenerProgramasDeFirebase { programasObtenidos ->
            programasPorDia = programasObtenidos
            isLoading = false
        }

    }

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

            // Selector de días
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

            if (isLoading) {
                // Mostrar loading mientras se cargan los datos
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF6B0000))
                }
            } else {
                // Lista de programas
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (programas.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No hay programas para este día",
                                    color = Color.Gray
                                )
                            }
                        }
                    } else {
                        items(programas) { programa ->
                            // Filtrar programas vacíos
                            if (programa.nombre.isNotEmpty() && programa.horario.isNotEmpty()) {
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
        }
    }
}