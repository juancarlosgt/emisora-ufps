package com.example.emisoraufps.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

// Modelo de datos para comunicador
data class ComunicadorDetail(
    val nombre: String = "",
    val rol: String = "",
    val programasCount: Int = 0,
    val horasAlAire: Int = 0,
    val seguidores: Int = 0,
    val biografia: String = "",
    val programasParticipo: List<String> = emptyList(),
    val capsulas: List<String> = emptyList(),
    val podcasts: List<String> = emptyList()
)

fun obtenerComunicadorDeFirebase(nombre: String, callback: (ComunicadorDetail?) -> Unit) {
    val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("comunicadores")

    database.get().addOnSuccessListener { snapshot ->
        var comunicador: ComunicadorDetail? = null

        for (dataSnapshot in snapshot.children) {
            val nombreFirebase = dataSnapshot.child("nombre").getValue(String::class.java) ?: ""
            if (nombreFirebase == nombre) {
                val rol = dataSnapshot.child("rol").getValue(String::class.java) ?: "Locutor UFPS Radio"
                val biografia = dataSnapshot.child("biografia").getValue(String::class.java) ?: ""

                comunicador = ComunicadorDetail(
                    nombre = nombreFirebase,
                    rol = rol,
                    biografia = biografia,
                    programasCount = 0,
                    horasAlAire = 0,
                    seguidores = 0,
                    programasParticipo = emptyList(),
                    capsulas = emptyList(),
                    podcasts = emptyList()
                )
                break
            }
        }
        callback(comunicador)
    }.addOnFailureListener {
        callback(null)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleComunicadorScreen(
    navController: NavController,
    nombre: String
) {
    var esFavorito by remember { mutableStateOf(false) }
    var comunicador by remember { mutableStateOf<ComunicadorDetail?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(nombre) {
        obtenerComunicadorDeFirebase(nombre) { comunicadorObtenido ->
            comunicador = comunicadorObtenido ?: ComunicadorDetail(nombre = nombre)
            isLoading = false
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color(0xFFB71C1C))
        }
    } else {
        val data = comunicador ?: ComunicadorDetail(nombre = nombre)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFDFDFD))
        ) {
            // Header rojo con fondo degradado
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(
                        color = Color(0xFFB71C1C) // Rojo oscuro similar al header
                    ),
                contentAlignment = Alignment.TopCenter
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Atrás",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Avatar circular grande
                    Box(
                        modifier = Modifier
                            .size(140.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(130.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF8B7ABA)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = data.nombre.firstOrNull()?.toString() ?: "?",
                                style = MaterialTheme.typography.displaySmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontSize = 48.sp
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Nombre del comunicador
                    Text(
                        text = data.nombre,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 22.sp
                        )
                    )

                    // Rol / subtítulo
                    Text(
                        text = data.rol,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    )
                }
            }

            // Contenido scrolleable blanco
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 80.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    EstadisticaCard(
                        numero = data.programasCount.toString(),
                        label = "Programas",
                        modifier = Modifier.weight(1f)
                    )
                    EstadisticaCard(
                        numero = "${data.horasAlAire}",
                        label = "Horas al aire",
                        modifier = Modifier.weight(1f)
                    )
                    EstadisticaCard(
                        numero = data.seguidores.toString(),
                        label = "Seguidores",
                        modifier = Modifier.weight(1f)
                    )
                }

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = DividerDefaults.Thickness,
                    color = DividerDefaults.color
                )

                Spacer(modifier = Modifier.height(16.dp))

                MenuOpcion(
                    icono = Icons.Default.Star,
                    texto = "Biografía",
                    descripcion = data.biografia
                )

                MenuOpcion(
                    icono = Icons.Default.Star,
                    texto = "Programas en los que participó",
                    descripcion = data.programasParticipo.joinToString(", ")
                )

                MenuOpcion(
                    icono = Icons.Default.Star,
                    texto = "Cápsulas Radiales",
                    descripcion = "${data.capsulas.size} cápsulas disponibles"
                )

                MenuOpcion(
                    icono = Icons.Default.Star,
                    texto = "Podcast Grabados",
                    descripcion = "${data.podcasts.size} podcast disponibles"
                )

                Spacer(modifier = Modifier.height(24.dp))
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Botón favorito
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFB71C1C),
                        modifier = Modifier
                            .size(56.dp)
                            .clickable { esFavorito = !esFavorito }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Favorito",
                            tint = Color.White,
                            modifier = Modifier
                                .size(28.dp)
                                //.align(Alignment.Center)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Botón compartir
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFB71C1C),
                        modifier = Modifier
                            .size(56.dp)
                            .clickable { /* TODO: Compartir */ }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Compartir",
                            tint = Color.White,
                            modifier = Modifier
                                .size(28.dp)
                                //.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EstadisticaCard(
    numero: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFB71C1C),
        modifier = modifier
            .height(100.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = numero,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 20.sp
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.White,
                    fontSize = 12.sp
                ),
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}

@Composable
fun MenuOpcion(
    icono: ImageVector,
    texto: String,
    descripcion: String? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { /* TODO: Acción */ }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = icono,
                contentDescription = texto,
                tint = Color(0xFFB71C1C),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = texto,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
        }

        if (descripcion != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = descripcion,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.Gray
                ),
                modifier = Modifier.padding(start = 36.dp)
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(top = 12.dp),
            thickness = DividerDefaults.Thickness,
            color = DividerDefaults.color
        )
    }
}

@Composable
fun HorizontalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = DividerDefaults.Thickness,
    color: Color = DividerDefaults.color
) {
    HorizontalDivider(
        modifier = modifier,
        thickness = thickness,
        color = color
    )
}
