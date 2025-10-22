package com.example.parcial1ppc.data.repository

import com.example.parcial1ppc.data.local.dao.PitStopDao
import com.example.parcial1ppc.data.local.entity.toDomain
import com.example.parcial1ppc.data.local.entity.toEntity
import com.example.parcial1ppc.data.model.PitStop
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PitStopRepository(
    private val pitStopDao: PitStopDao
) : IPitStopRepository {

    override fun getAllPitStops(): Flow<List<PitStop>> {
        return pitStopDao.getAllPitStops().map { list -> list.map { it.toDomain() } }
    }

    override fun searchPitStops(query: String): Flow<List<PitStop>> {
        return pitStopDao.searchPitStops(query).map { list -> list.map { it.toDomain() } }
    }

    override suspend fun savePitStop(pitStop: PitStop) {
        pitStopDao.insertPitStop(pitStop.toEntity())
    }

    override suspend fun deletePitStop(id: Int) {
        pitStopDao.deletePitStopById(id)
    }

    // NUEVO: get by id
    override fun getPitStopById(id: Int): Flow<PitStop?> {
        return pitStopDao.getPitStopById(id).map { entity -> entity?.toDomain() }
    }

    // Estadísticas (opcionales)
    fun getFastestPitStop(): Flow<Double?> = pitStopDao.getFastestPitStop()
    fun getSlowestPitStop(): Flow<Double?> = pitStopDao.getSlowestPitStop()
    fun getAveragePitStop(): Flow<Double?> = pitStopDao.getAveragePitStop()
    fun getTotalPitStops(): Flow<Int> = pitStopDao.getTotalPitStops()
    fun getFailedPitStops(): Flow<Int> = pitStopDao.getFailedPitStops()
}
