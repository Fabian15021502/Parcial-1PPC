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
