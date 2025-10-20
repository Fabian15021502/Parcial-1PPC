package com.example.parcial1ppc.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.parcial1ppc.data.local.entity.PitStopEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PitStopDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPitStop(pitStop: PitStopEntity)

    @Delete
    suspend fun deletePitStop(pitStop: PitStopEntity)

    @Query("SELECT * FROM pit_stops WHERE piloto LIKE '%' || :query || '%' OR escuderia LIKE '%' || :query || '%' ORDER BY id DESC")
    fun searchPitStops(query: String): Flow<List<PitStopEntity>>

    @Query("SELECT * FROM pit_stops ORDER BY id DESC")
    fun getAllPitStops(): Flow<List<PitStopEntity>>

    @Query("DELETE FROM pit_stops WHERE id = :id")
    suspend fun deletePitStopById(id: Int)

    // NUEVO: obtener por id (puede devolver null si no existe)
    @Query("SELECT * FROM pit_stops WHERE id = :id LIMIT 1")
    fun getPitStopById(id: Int): Flow<PitStopEntity?>

    // Estadísticas (si las usas)
    @Query("SELECT MIN(tiempoTotal) FROM pit_stops")
    fun getFastestPitStop(): Flow<Double?>

    @Query("SELECT MAX(tiempoTotal) FROM pit_stops")
    fun getSlowestPitStop(): Flow<Double?>

    @Query("SELECT AVG(tiempoTotal) FROM pit_stops")
    fun getAveragePitStop(): Flow<Double?>

    @Query("SELECT COUNT(*) FROM pit_stops")
    fun getTotalPitStops(): Flow<Int>

    @Query("SELECT COUNT(*) FROM pit_stops WHERE estado = 'fallido'")
    fun getFailedPitStops(): Flow<Int>
}
