package com.example.parcial1ppc.ui.resumen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CarRepair
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.parcial1ppc.ui.shared.BarChartComposable
import com.example.parcial1ppc.ui.shared.ResumenMetrica

// La implementación del Composable debe ir en este archivo
@Composable
fun ResumenScreen(
    viewModel: ResumenViewModel,
    onNavigateToRegistro: () -> Unit,
    onNavigateToListado: () -> Unit
) {
    // Observa el estado calculado por Fabian
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(

        topBar = {
            TopAppBar(title = { Text("Resumen de Pit Stops") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título principal
            Text(text = "Resumen de Pit Stops", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(24.dp))

            // Tarjeta de Métricas (Mockup)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    // Pit stop más rápido (2.3 s)
                    ResumenMetrica(
                        titulo = "Pit stop más rápido:",
                        valor = uiState.masRapido,
                        icon = { Icon(Icons.Filled.CarRepair, contentDescription = null, tint = Color(0xFFC92828)) }
                    )
                    // Promedio de tiempos (2.82 s)
                    ResumenMetrica(
                        titulo = "Promedio de tiempos:",
                        valor = uiState.promedio,
                        icon = { Icon(Icons.Filled.AccessTime, contentDescription = null, tint = Color.Gray) }
                    )
                    // Total de paradas (5)
                    ResumenMetrica(
                        titulo = "Total de paradas:",
                        valor = uiState.totalParadas.toString(),
                        icon = { Icon(Icons.Filled.Numbers, contentDescription = null, tint = Color.DarkGray) }
                    )
                }


            }


        }

        Spacer(modifier = Modifier.height(24.dp))

        // Gráfico de Últimos Pit Stops
        BarChartComposable(data = uiState.ultimosTiempos)

        Spacer(modifier = Modifier.weight(1f))

        // Botones (Mockup)
        Button(
            onClick = onNavigateToRegistro,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC92828)) // Rojo F1
        ) {
            Text("Registrar Pit Stop", color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(


            onClick = onNavigateToListado,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray) // Gris oscuro
        ) {
            Text("Ver Listado", color = Color.White)
        }
    }
    }
}
