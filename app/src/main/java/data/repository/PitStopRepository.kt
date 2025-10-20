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

    override fun getAllPitStops(): Flow<List<PitStop>> {
        return pitStopDao.getAllPitStops().map { entities ->
            entities.map { it.toDomain() }
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