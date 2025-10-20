// Archivo: ListadoViewModel.kt

package com.example.parcial1ppc.ui.listado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parcial1ppc.data.model.PitStop
import com.example.parcial1ppc.data.repository.IPitStopRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class ListadoUIState(
    val pitStops: List<PitStop> = emptyList(),
    val searchQuery: String = ""
)

class ListadoViewModel(
    private val repository: IPitStopRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")

    // Flujo de datos de la base de datos + búsqueda
    private val pitStopsFlow: Flow<List<PitStop>> = _searchQuery
        .debounce(300)
        .flatMapLatest { query ->
            if (query.isBlank()) {
                repository.getAllPitStops()
            } else {
                repository.searchPitStops(query)
            }
        }

    private val _uiState = MutableStateFlow(ListadoUIState())
    val uiState: StateFlow<ListadoUIState> = _uiState.asStateFlow()

    init {
        // Recolecta los pitstops del repositorio
        viewModelScope.launch {
            pitStopsFlow.collect { pitStops ->
                _uiState.update { it.copy(pitStops = pitStops) }
            }
        }

        // Recolecta el filtro de búsqueda
        viewModelScope.launch {
            _searchQuery.collect { query ->
                _uiState.update { it.copy(searchQuery = query) }
            }
        }
    }

    // Actualiza el texto de búsqueda
    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }

    // Elimina un pit stop
    fun deletePitStop(id: Int) {
        viewModelScope.launch {
            repository.deletePitStop(id)
        }
    }
}

