package com.example.parcial1ppc.ui.registro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parcial1ppc.data.model.PitStop
import com.example.parcial1ppc.data.repository.IPitStopRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class RegistroUiState(
    val id: Int? = null,
    val piloto: String = "",
    val escuderia: String = "",
    val tiempoTotal: String = "",
    val estado: String = "",
    val motivoFallo: String = "",
    val mecanicoPrincipal: String = "Carlos Pérez",
    val fechaHora: String = "2025-10-20 10:00",
    val cambioNeumaticos: String = "Soft",      // valor fijo según tu elección
    val numeroNeumaticosCambiados: Int = 4,     // valor fijo según tu elección
    val error: String? = null
)

class RegistroViewModel(
    private val repository: IPitStopRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState: StateFlow<RegistroUiState> = _uiState

    // Cargar datos de un PitStop existente para editar
    fun cargarPitStop(pitStop: PitStop) {
        _uiState.value = RegistroUiState(
            id = pitStop.id,
            piloto = pitStop.piloto,
            escuderia = pitStop.escuderia,
            tiempoTotal = pitStop.tiempoTotal.toString(),
            estado = pitStop.estado,
            motivoFallo = pitStop.motivoFallo ?: "",
            mecanicoPrincipal = pitStop.mecanicoPrincipal,
            fechaHora = pitStop.fechaHora,
            cambioNeumaticos = pitStop.cambioNeumaticos,
            numeroNeumaticosCambiados = pitStop.numeroNeumaticosCambiados
        )
    }

    fun onPilotoChange(newValue: String) { _uiState.value = _uiState.value.copy(piloto = newValue) }
    fun onEscuderiaChange(newValue: String) { _uiState.value = _uiState.value.copy(escuderia = newValue) }
    fun onTiempoTotalChange(newValue: String) { _uiState.value = _uiState.value.copy(tiempoTotal = newValue) }
    fun onEstadoChange(newValue: String) { _uiState.value = _uiState.value.copy(estado = newValue) }
    fun onMotivoFalloChange(newValue: String) { _uiState.value = _uiState.value.copy(motivoFallo = newValue) }
    fun onCambioNeumaticosChange(newValue: String) {
        _uiState.value = _uiState.value.copy(cambioNeumaticos = newValue)
    }

    fun onNumeroNeumaticosChange(newValue: String) {
        val numero = newValue.toIntOrNull() ?: 0
        _uiState.value = _uiState.value.copy(numeroNeumaticosCambiados = numero)
    }

    // Guarda o actualiza el pit stop (validación antes)
    fun savePitStop(onSuccess: () -> Unit, onErrorToast: (String) -> Unit) {
        val state = _uiState.value

        // Validación estricta (opción A)
        if (state.piloto.isBlank() ||
            state.escuderia.isBlank() ||
            state.tiempoTotal.isBlank() ||
            state.estado.isBlank()
        ) {
            val msg = "Por favor complete todos los campos obligatorios"
            _uiState.value = state.copy(error = msg)
            onErrorToast(msg)
            return
        }

        // Si estado es Fallido, motivoFallo es obligatorio
        if (state.estado.equals("Fallido", ignoreCase = true) && state.motivoFallo.isBlank()) {
            val msg = "Por favor indique el motivo del fallo"
            _uiState.value = state.copy(error = msg)
            onErrorToast(msg)
            return
        }

        val tiempoDouble = state.tiempoTotal.toDoubleOrNull()
        if (tiempoDouble == null) {
            val msg = "Tiempo total inválido"
            _uiState.value = state.copy(error = msg)
            onErrorToast(msg)
            return
        }

        viewModelScope.launch {
            try {
                val pitStop = PitStop(
                    id = state.id, // null -> insertar, no-null -> actualizar
                    piloto = state.piloto,
                    escuderia = state.escuderia,
                    tiempoTotal = tiempoDouble,
                    cambioNeumaticos = state.cambioNeumaticos,
                    numeroNeumaticosCambiados = state.numeroNeumaticosCambiados,
                    estado = state.estado,
                    motivoFallo = if (state.estado.equals("Fallido", ignoreCase = true)) state.motivoFallo else null,
                    mecanicoPrincipal = state.mecanicoPrincipal,
                    fechaHora = state.fechaHora
                )

                // Repositorio maneja insert/update (savePitStop es suspend)
                repository.savePitStop(pitStop)
                onSuccess()
            } catch (e: Exception) {
                val msg = "Error al guardar: ${e.message ?: "desconocido"}"
                _uiState.value = state.copy(error = msg)
                onErrorToast(msg)
            }
        }
    }
}
