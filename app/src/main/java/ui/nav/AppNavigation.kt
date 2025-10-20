package com.example.parcial1ppc.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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

object Destinations {
    const val RESUMEN = "resumen"
    const val LISTADO = "listado"
    const val REGISTRO = "registro"
    const val REGISTRO_WITH_ID = "registro/{pitStopId}"
}

class PitStopViewModelFactory(private val repository: IPitStopRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ResumenViewModel::class.java) ->
                ResumenViewModel(repository) as T

            modelClass.isAssignableFrom(ListadoViewModel::class.java) ->
                ListadoViewModel(repository) as T

            modelClass.isAssignableFrom(RegistroViewModel::class.java) ->
                RegistroViewModel(repository) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}

@Composable
fun AppRoot() {
    val context = LocalContext.current
    val database = remember { AppDatabase.getDatabase(context) }
    val repository: IPitStopRepository = remember { PitStopRepository(database.pitStopDao()) }
    val factory = remember { PitStopViewModelFactory(repository) }

    AppNavigation(factory = factory, repository = repository)
}

@Composable
fun AppNavigation(factory: ViewModelProvider.Factory, repository: IPitStopRepository) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Destinations.RESUMEN) {

        composable(Destinations.RESUMEN) {
            val viewModel: ResumenViewModel = viewModel(factory = factory)
            ResumenScreen(
                viewModel = viewModel,
                onNavigateToRegistro = { navController.navigate(Destinations.REGISTRO) },
                onNavigateToListado = { navController.navigate(Destinations.LISTADO) }
            )
        }

        composable(Destinations.LISTADO) {
            val viewModel: ListadoViewModel = viewModel(factory = factory)
            ListadoScreen(
                viewModel = viewModel,
                // ahora permite pasar PitStop? para edición o null para nuevo
                onNavigateToRegistro = { pitStop ->
                    if (pitStop == null) {
                        navController.navigate(Destinations.REGISTRO)
                    } else {
                        // navegar con id (si id es null usamos -1, la pantalla ignorará -1)
                        val id = pitStop.id ?: -1
                        navController.navigate("registro/$id")
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        // Ruta para crear nuevo
        composable(Destinations.REGISTRO) {
            val viewModel: RegistroViewModel = viewModel(factory = factory)
            RegistroScreen(
                viewModel = viewModel,
                onSaveSuccess = { navController.popBackStack() },
                onCancel = { navController.popBackStack() }
            )
        }

        // Ruta para editar (recibe pitStopId)
        composable(
            Destinations.REGISTRO_WITH_ID,
            arguments = listOf(navArgument("pitStopId") { type = NavType.IntType; defaultValue = -1 })
        ) { backStackEntry ->
            val viewModel: RegistroViewModel = viewModel(factory = factory)
            // extraemos id
            val pitStopId = backStackEntry.arguments?.getInt("pitStopId") ?: -1
            // Si id válido, cargamos el pit stop desde el repositorio y lo pasamos al viewmodel
            if (pitStopId > 0) {
                // lanzamos efecto para cargar el pitStop (una sola vez)
                androidx.compose.runtime.LaunchedEffect(pitStopId) {
                    repository.getPitStopById(pitStopId).collect { pitStop ->
                        pitStop?.let { viewModel.cargarPitStop(it) }
                    }
                }
            }

            RegistroScreen(
                viewModel = viewModel,
                onSaveSuccess = { navController.popBackStack() },
                onCancel = { navController.popBackStack() }
            )
        }
    }
}
