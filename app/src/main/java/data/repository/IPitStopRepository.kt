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

    // Requisito: Guardar pit stop (se usa para insertar y actualizar)
    /**
     * Guarda un nuevo PitStop o actualiza uno existente.
     */
    suspend fun savePitStop(pitStop: PitStop)

    // Requisito: Eliminar pit stop
    /**
     * Elimina un PitStop de la base de datos por su ID.
     */
    suspend fun deletePitStop(id: Int)
}