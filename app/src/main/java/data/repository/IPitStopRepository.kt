package com.example.parcial1ppc.data.repository

import com.example.parcial1ppc.data.model.PitStop
import kotlinx.coroutines.flow.Flow

interface IPitStopRepository {
    fun getAllPitStops(): Flow<List<PitStop>>
    fun searchPitStops(query: String): Flow<List<PitStop>>
    suspend fun savePitStop(pitStop: PitStop)
    suspend fun deletePitStop(id: Int)

    // NUEVO: obtener un pit stop por id (devuelve Flow para reaccionar a cambios)
    fun getPitStopById(id: Int): Flow<PitStop?>
}
