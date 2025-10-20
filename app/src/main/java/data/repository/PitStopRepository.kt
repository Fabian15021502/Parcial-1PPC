package com.example.parcial1ppc.data.repository

import com.example.parcial1ppc.data.local.dao.PitStopDao
import com.example.parcial1ppc.data.local.entity.PitStopEntity
import com.example.parcial1ppc.data.local.entity.toDomain
import com.example.parcial1ppc.data.local.entity.toEntity
import com.example.parcial1ppc.data.model.PitStop
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface IPitStopRepository {
    fun getAllPitStops(): Flow<List<PitStop>>
    fun searchPitStops(query: String): Flow<List<PitStop>>
    suspend fun savePitStop(pitStop: PitStop)
    suspend fun deletePitStop(id: Int)
}

class PitStopRepository(private val pitStopDao: PitStopDao) : IPitStopRepository {

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
        return pitStopDao.searchPitStops(query).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun savePitStop(pitStop: PitStop) {
        pitStopDao.insert(pitStop.toEntity())
    }

    override suspend fun deletePitStop(id: Int) {
        val pitStopToDelete = PitStopEntity(id = id, piloto = "", escuderia = "", tiempoTotal = 0.0, cambioNeumaticos = "", numeroNeumaticosCambiados = 0, estado = "", motivoFallo = null, mecanicoPrincipal = "", fechaHora = "")
        pitStopDao.delete(pitStopToDelete)
    }
}