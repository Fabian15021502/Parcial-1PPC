package com.example.parcial1ppc.ui.registro

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun RegistroScreen(
    viewModel: RegistroViewModel,
    onSaveSuccess: () -> Unit,
    onCancel: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar Pit Stop") },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Cancelar")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            item {
                // Título del Mockup
                Text(text = "Registrar Pit Stop", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))

                // Campos de entrada (Mockup: Piloto, Escudería, Tiempo Total)
                Text(text = "PILOTO", style = MaterialTheme.typography.labelSmall)
                OutlinedTextField(
                    value = uiState.piloto,
                    onValueChange = viewModel::onPilotoChange,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "ESCUDERÍA", style = MaterialTheme.typography.labelSmall)
                OutlinedTextField(
                    value = uiState.escuderia,
                    onValueChange = viewModel::onEscuderiaChange,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "TIEMPO TOTAL (S)", style = MaterialTheme.typography.labelSmall)
                OutlinedTextField(
                    value = uiState.tiempoTotal,
                    onValueChange = viewModel::onTiempoTotalChange,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberDecimal)
                )

                // MOCKUP: Cambio de Neumáticos y Número de Neumáticos Cambiados (Usaremos solo texto por simplicidad inicial)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "CAMBIO DE NEUMÁTICOS: Soft", style = MaterialTheme.typography.bodyMedium)
                Text(text = "NÚMERO DE NEUMÁTICOS CAMBIADOS: 4", style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(8.dp))
                // Estado (Simple TextField, debería ser Dropdown)
                Text(text = "ESTADO (Ok / Fallido)", style = MaterialTheme.typography.labelSmall)
                OutlinedTextField(
                    value = uiState.estado,
                    onValueChange = viewModel::onEstadoChange,
                    modifier = Modifier.fillMaxWidth()
                )

                // Motivo del Fallo (Solo si el estado es Fallido)
                if (uiState.estado.equals("Fallido", ignoreCase = true)) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "MOTIVO DEL FALLO", style = MaterialTheme.typography.labelSmall)
                    OutlinedTextField(
                        value = uiState.motivoFallo,
                        onValueChange = viewModel::onMotivoFalloChange,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Información no editable (Mockup)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text(text = "MECÁNICO PRINCIPAL", style = MaterialTheme.typography.labelSmall)
                        Text(text = uiState.mecanicoPrincipal, style = MaterialTheme.typography.bodyMedium)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = "FECHA Y HORA DEL PIT STOP", style = MaterialTheme.typography.labelSmall)
                        Text(text = uiState.fechaHora, style = MaterialTheme.typography.bodyMedium)
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))

                // Requisito: GUARDAR y CANCELAR (Mockup)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        onClick = onCancel,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                        modifier = Modifier.weight(1f).height(50.dp)
                    ) {
                        Text("CANCELAR", color = Color.White)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { viewModel.savePitStop(onSaveSuccess) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC92828)), // Rojo F1
                        modifier = Modifier.weight(1f).height(50.dp)
                    ) {
                        Text("GUARDAR", color = Color.White)
                    }
                }
            }
        }
    }
}