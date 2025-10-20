package com.example.parcial1ppc.data.local.dao

import androidx.room.*
import com.example.parcial1ppc.data.local.entity.PitStopEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PitStopDao {
    // Requisito: Guardar pit stop (REPLACE maneja inserción y actualización)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pitStop: PitStopEntity)

    // Requisito: Eliminar pit stop
    @Delete
    suspend fun delete(pitStop: PitStopEntity)

    // Requisito: Mostrar listado de pit stops
    @Query("SELECT * FROM pit_stops ORDER BY fechaHora DESC")
    fun getAllPitStops(): Flow<List<PitStopEntity>>

    // Requisito: Buscar pit stop
    @Query("SELECT * FROM pit_stops WHERE piloto LIKE '%' || :query || '%' OR escuderia LIKE '%' || :query || '%' ORDER BY fechaHora DESC")
    fun searchPitStops(query: String): Flow<List<PitStopEntity>>
}