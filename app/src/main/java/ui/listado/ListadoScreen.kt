package com.example.parcial1ppc.ui.listado

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.parcial1ppc.data.model.PitStop
import com.example.parcial1ppc.ui.shared.TableCell

@Composable
fun ListadoScreen(
    viewModel: ListadoViewModel,
    onNavigateToRegistro: () -> Unit,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Listado de Pit Stops") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        },
        // Botón flotante para registrar (Acceso rápido)
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToRegistro) {
                Icon(Icons.Filled.Add, contentDescription = "Registrar")
            }
        }
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .padding(horizontal = 16.dp)) {

            // Requisito: Buscar pit stop
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = viewModel::onSearchQueryChange,
                label = { Text("Q Buscar (Piloto o Equipo)") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Buscar") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            // Encabezados de la tabla (Mockup)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray.copy(alpha = 0.5f))
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TableCell(text = "Núm.", weight = 0.15f, align = Alignment.Start, fontWeight = FontWeight.Bold)
                TableCell(text = "Piloto", weight = 0.3f, align = Alignment.Start, fontWeight = FontWeight.Bold)
                TableCell(text = "Tiempo(s)", weight = 0.2f, align = Alignment.End, fontWeight = FontWeight.Bold)
                TableCell(text = "Estad", weight = 0.15f, align = Alignment.Center, fontWeight = FontWeight.Bold)
                TableCell(text = "Acc", weight = 0.2f, align = Alignment.Center, fontWeight = FontWeight.Bold)
            }

            // Requisito: Mostrar listado de pit stops
            LazyColumn {
                items(uiState.pitStops, key = { it.id }) { pitStop ->
                    PitStopRow(pitStop = pitStop, viewModel = viewModel)
                    Divider()
                }
            }
        }
    }
}

// Fila de datos individual con acciones
@Composable
fun PitStopRow(pitStop: PitStop, viewModel: ListadoViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TableCell(text = pitStop.id.toString(), weight = 0.15f, align = Alignment.Start)
        TableCell(text = pitStop.piloto, weight = 0.3f, align = Alignment.Start)
        TableCell(text = "%.1f".format(pitStop.tiempoTotal), weight = 0.2f, align = Alignment.End)
        TableCell(
            text = pitStop.estado, weight = 0.15f, align = Alignment.Center,
            color = if (pitStop.estado == "Ok") Color(0xFF4CAF50) else Color(0xFFF44336) 
        )

        // Botones de Acción (Editar y Eliminar)
        Row(
            modifier = Modifier.weight(0.2f),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Icon(
                Icons.Filled.Edit,
                contentDescription = "Editar",
                modifier = Modifier.clickable { /* Tarea pendiente: Implementar edición */ },
                tint = Color.Gray
            )
            // Requisito: Eliminar pit stop
            Icon(
                Icons.Filled.Delete,
                contentDescription = "Eliminar",
                modifier = Modifier.clickable { viewModel.deletePitStop(pitStop.id) },
                tint = Color.Red
            )
        }
    }
}
