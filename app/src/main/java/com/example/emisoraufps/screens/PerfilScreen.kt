package com.example.emisoraufps.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.emisoraufps.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen() {
    var notificacionesActivas by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF8B0000), // rojo oscuro
                                Color(0xFFD32F2F)  // rojo brillante
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Perfil Usuario",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
        }

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header del perfil con fondo degradado y avatar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF8B0000), // rojo oscuro arriba
                                Color(0xFFD32F2F)  // rojo brillante o vino abajo
                            )
                        ),
                        shape = RoundedCornerShape(
                            bottomStart = 24.dp,
                            bottomEnd = 24.dp
                        )
                    )
                    .padding(top = 48.dp, bottom = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    // Imagen del avatar
                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.3f)), // efecto brillo o borde suave
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.hombre), // avatar temporal
                            contentDescription = "Avatar de usuario",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Nombre del usuario
                    Text(
                        text = "Matias Herrera",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )

                    // Subtítulo o rol
                    Text(
                        text = "Usuario UFPS Radio",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))



            // Botón de perfil personal
            Box(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(0.6f)
                    .height(40.dp)

                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF8B0000), // rojo oscuro
                                Color(0xFFD32F2F)  // rojo más brillante
                            )
                        ),
                        shape = RoundedCornerShape(50)
                    )
            ) {
                Button(
                    onClick = { /* TODO: Acción */ },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent, // permite ver el degradado
                        contentColor = Color.White          // texto blanco
                    ),
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        "Perfil Personal",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            // Notificaciones
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Notificaciones", style = MaterialTheme.typography.titleMedium)
                        Switch(
                            checked = notificacionesActivas,
                            onCheckedChange = { notificacionesActivas = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,           // color del círculo (thumb)
                                checkedTrackColor = Color(0xFFD32F2F),    // color de la pista (activo)
                                uncheckedThumbColor = Color.Gray,          // color del círculo inactivo
                                uncheckedTrackColor = Color.LightGray      // color de la pista inactiva
                            )
                        )
                    }

                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    PerfilOpcionItem("Editar nombre")
                    PerfilOpcionItem("Cambiar contraseña")
                    PerfilOpcionItem("Mis programas favoritos")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botones inferiores
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                OutlinedButton(
                    onClick = { /* TODO: Compartir app */ },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(50)
                ) {
                    Text("Compartir la aplicación ↗", textAlign = TextAlign.Center)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { /* TODO: Contacto */ },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Text("Contacto", color = MaterialTheme.colorScheme.onSecondaryContainer)
                }
            }
        }
    }
}

@Composable
fun PerfilOpcionItem(texto: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* TODO: acción */ }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = texto,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "Ir a $texto",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
