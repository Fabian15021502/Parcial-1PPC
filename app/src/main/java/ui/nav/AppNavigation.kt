package com.example.parcial1ppc.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.parcial1ppc.data.local.database.AppDatabase
import com.example.parcial1ppc.data.repository.IPitStopRepository
import com.example.parcial1ppc.data.repository.PitStopRepository
import com.example.parcial1ppc.ui.listado.ListadoScreen
import com.example.parcial1ppc.ui.listado.ListadoViewModel
import com.example.parcial1ppc.ui.registro.RegistroScreen
import com.example.parcial1ppc.ui.registro.RegistroViewModel
import com.example.parcial1ppc.ui.resumen.ResumenScreen
import com.example.parcial1ppc.ui.resumen.ResumenViewModel

// Define las rutas (Destinos) para la navegación
object Destinations {
    const val RESUMEN = "resumen"
    const val LISTADO = "listado"
    const val REGISTRO = "registro"
}

// Factoría para inyectar el Repositorio en los ViewModels
class PitStopViewModelFactory(private val repository: IPitStopRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResumenViewModel::class.java)) return ResumenViewModel(repository) as T
        if (modelClass.isAssignableFrom(ListadoViewModel::class.java)) return ListadoViewModel(repository) as T
        if (modelClass.isAssignableFrom(RegistroViewModel::class.java)) return RegistroViewModel(repository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

// Componente Raíz que inicializa la DB, Repositorio y Factoría
@Composable
fun AppRoot(
    // Se asume que AppRoot es llamado desde MainActivity
) {
    val context = LocalContext.current

    // 1. Inicializa la DB (usa el Singleton de Fabian)
    val database = remember { AppDatabase.getDatabase(context) }

    // 2. Inicializa el Repositorio (usa la implementación de Fabian, inyectando el DAO)
    val repository: IPitStopRepository = remember { PitStopRepository(database.pitStopDao()) }

    // 3. Inicializa la Factoría para inyectar la dependencia en los ViewModels
    val factory = remember { PitStopViewModelFactory(repository) }

    AppNavigation(factory)
}

// Componente que define el Grafo de Navegación
@Composable
fun AppNavigation(factory: ViewModelProvider.Factory) {
    val navController = rememberNavController()

    // El NavHost define la estructura de navegación, comenzando en la pantalla de resumen
    NavHost(navController = navController, startDestination = Destinations.RESUMEN) {

        // Pantalla de Resumen
        composable(Destinations.RESUMEN) {
            val viewModel: ResumenViewModel = viewModel(factory = factory)
            ResumenScreen(
                viewModel = viewModel,
                onNavigateToRegistro = { navController.navigate(Destinations.REGISTRO) },
                onNavigateToListado = { navController.navigate(Destinations.LISTADO) }
            )
        }

        // Pantalla de Listado
        composable(Destinations.LISTADO) {
            val viewModel: ListadoViewModel = viewModel(factory = factory)
            ListadoScreen(
                viewModel = viewModel,
                onNavigateToRegistro = { navController.navigate(Destinations.REGISTRO) },
                onBack = { navController.popBackStack() }
            )
        }

        // Pantalla de Registro/Edición
        composable(Destinations.REGISTRO) {
            val viewModel: RegistroViewModel = viewModel(factory = factory)
            RegistroScreen(
                viewModel = viewModel,
                onSaveSuccess = { navController.popBackStack() }, // Navega atrás al guardar
                onCancel = { navController.popBackStack() } // Navega atrás al cancelar
            )
        }
    }
}