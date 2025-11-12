package com.example.emisoraufps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.emisoraufps.screens.*
import com.example.emisoraufps.ui.theme.EmisoraUFPSTheme
import com.example.emisoraufps.screens.DetalleProgramaScreen
import com.example.emisoraufps.screens.PersistentMiniPlayer
import com.example.emisoraufps.PlayerViewModel
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmisoraUFPSTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

// Datos para la navegación
sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Inicio : Screen("inicio", "Inicio", Icons.Default.Home)
    object Programacion : Screen("programacion", "Programación", Icons.Default.Schedule)
    object Comunicadores : Screen("comunicadores", "Comunicadores", Icons.Default.Mic)
    object Perfil : Screen("perfil", "Perfil", Icons.Default.Person)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Añadir el innerPadding si es necesario
        ) {
            NavigationHost(navController, Modifier.padding(innerPadding))
            val playerViewModel: PlayerViewModel = viewModel()
            PersistentMiniPlayer(
                viewModel = playerViewModel,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            )
        }

    }
}

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Inicio.route,
        modifier = modifier
    ) {
        composable(Screen.Inicio.route) {
            InicioScreen()
        }
        composable(Screen.Programacion.route) {
            ProgramacionScreen(navController)
        }
        composable(Screen.Comunicadores.route) {
            ComunicadoresScreen(navController)
        }
        composable(Screen.Perfil.route) {
            PerfilScreen()
        }
        composable(
            "detalle/{nombre}/{horario}",
        ) { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            val horario = backStackEntry.arguments?.getString("horario") ?: ""
            DetalleProgramaScreen(navController, nombre, horario)
        }
        composable(
            "comunicador/{nombre}",
        ) { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            DetalleComunicadorScreen(navController, nombre)
        }

    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        Screen.Inicio,
        Screen.Programacion,
        Screen.Comunicadores,
        Screen.Perfil
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = {
                    Text(
                        text = screen.title,
                        style = MaterialTheme.typography.labelSmall // cambiar tamano texto
                    )
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {

                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}