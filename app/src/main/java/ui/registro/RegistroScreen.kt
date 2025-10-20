package com.example.parcial1ppc.ui.registro

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    viewModel: RegistroViewModel,
    onSaveSuccess: () -> Unit,
    onCancel: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("Completado", "Fallido")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar Pit Stop") },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Cancelar")
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
                Text(text = "Registrar Pit Stop", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))

                // PILOTO
                Text(text = "PILOTO", style = MaterialTheme.typography.labelSmall)
                OutlinedTextField(
                    value = uiState.piloto,
                    onValueChange = viewModel::onPilotoChange,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))
                // ESCUDERÍA
                Text(text = "ESCUDERÍA", style = MaterialTheme.typography.labelSmall)
                OutlinedTextField(
                    value = uiState.escuderia,
                    onValueChange = viewModel::onEscuderiaChange,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))
                // TIEMPO TOTAL
                Text(text = "TIEMPO TOTAL (S)", style = MaterialTheme.typography.labelSmall)
                OutlinedTextField(
                    value = uiState.tiempoTotal,
                    onValueChange = viewModel::onTiempoTotalChange,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))
                // CAMBIO NEUMÁTICOS
                Text(text = "CAMBIO DE NEUMÁTICOS", style = MaterialTheme.typography.labelSmall)
                OutlinedTextField(
                    value = uiState.cambioNeumaticos,
                    onValueChange = { viewModel.onCambioNeumaticosChange(it) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))
                // NÚMERO NEUMÁTICOS
                Text(text = "NÚMERO DE NEUMÁTICOS", style = MaterialTheme.typography.labelSmall)
                OutlinedTextField(
                    value = uiState.numeroNeumaticosCambiados.toString(),
                    onValueChange = { viewModel.onNumeroNeumaticosChange(it) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))
                // ESTADO DROPDOWN
                Text(text = "ESTADO", style = MaterialTheme.typography.labelSmall)
                ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                    OutlinedTextField(
                        value = uiState.estado,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        options.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = { Text(selectionOption) },
                                onClick = {
                                    viewModel.onEstadoChange(selectionOption)
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                if (uiState.estado == "Fallido") {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "MOTIVO DEL FALLO", style = MaterialTheme.typography.labelSmall)
                    OutlinedTextField(
                        value = uiState.motivoFallo,
                        onValueChange = viewModel::onMotivoFalloChange,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("MECÁNICO PRINCIPAL", style = MaterialTheme.typography.labelSmall)
                        Text(uiState.mecanicoPrincipal)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("FECHA Y HORA", style = MaterialTheme.typography.labelSmall)
                        Text(uiState.fechaHora)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        onClick = onCancel,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                    ) {
                        Text("CANCELAR", color = Color.White)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            viewModel.savePitStop(onSuccess = {
                                Toast.makeText(context, "Registro guardado", Toast.LENGTH_SHORT).show()
                                onSaveSuccess()
                            }, onErrorToast = {
                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                            })
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC92828)),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                    ) {
                        Text("GUARDAR", color = Color.White)
                    }
                }
            }
        }
    }
}
