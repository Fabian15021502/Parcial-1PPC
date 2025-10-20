package com.example.parcial1ppc.data.repository

import com.example.parcial1ppc.data.model.PitStop
import kotlinx.coroutines.flow.Flow
interface IPitStopRepository {

    // Requisito: Mostrar listado de pit stops
    /**
     * Retorna un flujo (Flow) de todos los Pit Stops, actualizándose en tiempo real.
     */
    fun getAllPitStops(): Flow<List<PitStop>>

    // Requisito: Buscar pit stop
    /**
     * Retorna un flujo de Pit Stops filtrados por un término de búsqueda (piloto o escudería).
     */
    fun searchPitStops(query: String): Flow<List<PitStop>>
