package com.example.parcial1ppc.ui.resumen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parcial1ppc.data.repository.IPitStopRepository
import kotlinx.coroutines.flow.*

data class ResumenUiState(
    val masRapido: String = "0.0 s",
    val promedio: String = "0.00 s",
    val totalParadas: Int = 0,
    val ultimosTiempos: List<Double> = emptyList()
)

class ResumenViewModel(repository: IPitStopRepository) : ViewModel() {

    val uiState: StateFlow<ResumenUiState> = repository.getAllPitStops()
        .map { pitStops ->
            val stopsOk = pitStops.filter { it.estado == "Ok" }
            val tiempos = stopsOk.map { it.tiempoTotal }

            ResumenUiState(
                masRapido = "${tiempos.minOrNull()?.let { "%.1f".format(it) } ?: "0.0"} s",
                promedio = "${tiempos.average().let { "%.2f".format(it) }} s",
                totalParadas = pitStops.size,
                ultimosTiempos = pitStops.take(5).map { it.tiempoTotal }
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ResumenUiState()
        )
}