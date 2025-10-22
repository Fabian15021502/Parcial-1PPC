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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListadoScreen(
    viewModel: ListadoViewModel,
    onNavigateToRegistro: (PitStop?) -> Unit,
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
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigateToRegistro(null) }) {
                Icon(Icons.Filled.Add, contentDescription = "Registrar")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = viewModel::onSearchQueryChange,
                label = { Text("Buscar (Piloto o Equipo)") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Buscar") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray.copy(alpha = 0.5f))
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TableCell("Núm.", 0.15f, Alignment.Start, FontWeight.Bold)
                TableCell("Piloto", 0.3f, Alignment.Start, FontWeight.Bold)
                TableCell("Tiempo(s)", 0.2f, Alignment.End, FontWeight.Bold)
                TableCell("Estad", 0.15f, Alignment.CenterHorizontally, FontWeight.Bold)
                TableCell("Acc", 0.2f, Alignment.CenterHorizontally, FontWeight.Bold)
            }

            LazyColumn {
                items(uiState.pitStops, key = { it.id ?: 0 }) { pitStop ->
                    PitStopRow(pitStop, viewModel, onNavigateToRegistro)
                    Divider()
                }
            }
        }
    }
}

@Composable
fun PitStopRow(
    pitStop: PitStop,
    viewModel: ListadoViewModel,
    onNavigateToRegistro: (PitStop) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TableCell(pitStop.id?.toString() ?: "-", 0.15f, Alignment.Start)
        TableCell(pitStop.piloto, 0.3f, Alignment.Start)
        TableCell("%.1f".format(pitStop.tiempoTotal), 0.2f, Alignment.End)
        TableCell(
            pitStop.estado,
            0.15f,
            Alignment.CenterHorizontally,
            color = if (pitStop.estado == "Ok" || pitStop.estado == "Completado") Color(0xFF4CAF50) else Color(0xFFF44336)
        )

        Row(
            modifier = Modifier.weight(0.2f),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Icon(
                Icons.Filled.Edit,
                contentDescription = "Editar",
                modifier = Modifier.clickable {
                    onNavigateToRegistro(pitStop) // navega a la pantalla de registro con el pitStop
                },
                tint = Color.Gray
            )
            Icon(
                Icons.Filled.Delete,
                contentDescription = "Eliminar",
                modifier = Modifier.clickable {
                    pitStop.id?.let { viewModel.deletePitStop(it) }
                },
                tint = Color.Red
            )
        }
    }
}
