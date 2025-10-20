// Paquete: com.example.parcial1ppc.data.repository

package com.example.parcial1ppc.data.repository

import com.example.parcial1ppc.data.local.dao.PitStopDao
import com.example.parcial1ppc.data.local.entity.PitStopEntity
import com.example.parcial1ppc.data.local.entity.toDomain
import com.example.parcial1ppc.data.local.entity.toEntity
import com.example.parcial1ppc.data.model.PitStop
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlin.random.Random

// Interfaz para el repositorio (ideal para pruebas unitarias)
interface IPitStopRepository {
    val pitStops: Flow<List<PitStop>>
    suspend fun addPitStop(pitStop: PitStop)
    suspend fun deletePitStop(id: Int) // Implementa requisito: Eliminar pit stop
    fun searchPitStops(query: String): Flow<List<PitStop>> // Implementa requisito: Buscar pit stop
}

// Implementación temporal en memoria con datos de ejemplo
class PitStopRepository : IPitStopRepository {

    // 1. Datos de ejemplo basados en el Listado del Mockup
    private val initialList = listOf(
        PitStop(1, "Oliveiro", "Mercedes", 2.4, "Soft", 4, "Ok", null, "John Doe", "12/05/2024 14:30"),
        PitStop(2, "James", "Ferrari", 2.8, "Medium", 4, "Fallido", "Fallo tuerca", "Jane Smith", "12/05/2024 14:40"),
        PitStop(3, "Mark", "Red Bull", 2.3, "Hard", 4, "Ok", null, "Max B.", "12/05/2024 14:50"),
        PitStop(4, "Sebastian", "Aston Martin", 3.1, "Soft", 4, "Fallido", "Error gato", "Peter K.", "12/05/2024 15:00"),
        PitStop(5, "Lucas", "McLaren", 3.0, "Medium", 4, "Fallido", "Neumático duro", "Ana C.", "12/05/2024 15:10")
    )

    // MutableStateFlow simula la fuente de datos reactiva (como un LiveData o Flow de Room)
    private val _pitStops = MutableStateFlow(initialList)
    override val pitStops: Flow<List<PitStop>> = _pitStops

    override suspend fun addPitStop(pitStop: PitStop) {
        // Implementa requisito: Guardar pit stop
        _pitStops.update { currentList ->
            // Simular un nuevo ID (si fuera una edición, el ID ya existiría)
            val newId = currentList.maxOfOrNull { it.id }?.plus(1) ?: 1
            currentList + pitStop.copy(id = newId)
        }
    }

    override suspend fun deletePitStop(id: Int) {
        // Implementa requisito: Eliminar pit stop
        _pitStops.update { currentList ->
            currentList.filter { it.id != id }
        }
    }

    override fun searchPitStops(query: String): Flow<List<PitStop>> {
        // Implementa requisito: Buscar pit stop
        return _pitStops.map { list ->
            if (query.isBlank()) {
                list
            } else {
                list.filter {
                    // Búsqueda simple por Piloto o Escudería
                    it.piloto.contains(query, ignoreCase = true) ||
                            it.escuderia.contains(query, ignoreCase = true)
                }
            }
        }
    }
}