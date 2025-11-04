package com.example.emisoraufps.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Modelo de datos para comunicador
data class ComunicadorDetail(
    val id: Int,
    val nombre: String,
    val rol: String,
    val programasCount: Int,
    val horasAlAire: Int,
    val seguidores: Int,
    val biografia: String,
    val programasParticipo: List<String>,
    val capsulas: List<String>,
    val podcasts: List<String>
)

private val comunicadoresData = mapOf(
    "Ing Matias" to ComunicadorDetail(
        id = 1,
        nombre = "Ing Matias",
        rol = "Locutor UFPS Radio",
        programasCount = 3,
        horasAlAire = 160,
        seguidores = 20,
        biografia = "Ingeniero apasionado por la radio y la música. Con experiencia en comunicación y presentación de programas.",
        programasParticipo = listOf("Bailando sin cesar", "Ritmos digitales", "Voces del campus"),
        capsulas = listOf("Podcast 1", "Podcast 2", "Podcast 3"),
        podcasts = listOf("Episodio 1", "Episodio 2")
    ),
    "Ing Adarme" to ComunicadorDetail(
        id = 2,
        nombre = "Ing Adarme",
        rol = "Locutor UFPS Radio",
        programasCount = 2,
        horasAlAire = 140,
        seguidores = 15,
        biografia = "Especialista en programación y estructuras de datos. Divulga contenido técnico de forma entretenida.",
        programasParticipo = listOf("Data Structures", "Código en vivo"),
        capsulas = listOf("Cápsula 1", "Cápsula 2"),
        podcasts = listOf("Episodio 1")
    ),
    "Ing Pilar Rojas" to ComunicadorDetail(
        id = 3,
        nombre = "Ing Pilar Rojas",
        rol = "Locutora UFPS Radio",
        programasCount = 4,
        horasAlAire = 200,
        seguidores = 25,
        biografia = "Locutora con gran trayectoria en radio. Especializada en música y entretenimiento.",
        programasParticipo = listOf("Apilando canciones", "Éxitos del momento", "Nostalgia musical", "Ritmo y poesía"),
        capsulas = listOf("Cápsula 1", "Cápsula 2", "Cápsula 3"),
        podcasts = listOf("Episodio 1", "Episodio 2", "Episodio 3")
    ),
    "Diany" to ComunicadorDetail(
        id = 4,
        nombre = "Diany",
        rol = "Locutora UFPS Radio",
        programasCount = 2,
        horasAlAire = 120,
        seguidores = 18,
        biografia = "Apasionada por los éxitos de los 80. Trae nostalgia y buen humor a cada emisión.",
        programasParticipo = listOf("Canciones de los 80", "Retro Hits"),
        capsulas = listOf("Cápsula 1", "Cápsula 2"),
        podcasts = listOf("Episodio 1")
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleComunicadorScreen(
    navController: NavController,
    nombre: String
) {
    var esFavorito by remember { mutableStateOf(false) }

    val comunicador = comunicadoresData[nombre] ?: ComunicadorDetail(
        id = 0,
        nombre = nombre,
        rol = "Locutor UFPS Radio",
        programasCount = 0,
        horasAlAire = 0,
        seguidores = 0,
        biografia = "",
        programasParticipo = emptyList(),
        capsulas = emptyList(),
        podcasts = emptyList()
    )

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
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
                            text = comunicador.nombre.firstOrNull()?.toString() ?: "?",
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
                    text = comunicador.nombre,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 22.sp
                    )
                )

                // Rol / subtítulo
                Text(
                    text = comunicador.rol,
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
                    numero = comunicador.programasCount.toString(),
                    label = "Programas",
                    modifier = Modifier.weight(1f)
                )
                EstadisticaCard(
                    numero = "${comunicador.horasAlAire}",
                    label = "Horas al aire",
                    modifier = Modifier.weight(1f)
                )
                EstadisticaCard(
                    numero = comunicador.seguidores.toString(),
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
                descripcion = comunicador.biografia
            )

            MenuOpcion(
                icono = Icons.Default.Star,
                texto = "Programas en los que participó",
                descripcion = comunicador.programasParticipo.joinToString(", ")
            )

            MenuOpcion(
                icono = Icons.Default.Star,
                texto = "Cápsulas Radiales",
                descripcion = "${comunicador.capsulas.size} cápsulas disponibles"
            )

            MenuOpcion(
                icono = Icons.Default.Star,
                texto = "Podcast Grabados",
                descripcion = "${comunicador.podcasts.size} podcast disponibles"
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
