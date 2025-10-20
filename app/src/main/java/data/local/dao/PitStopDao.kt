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
