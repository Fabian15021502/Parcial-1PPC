package com.example.parcial1ppc

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.parcial1ppc.data.local.dao.PitStopDao
import com.example.parcial1ppc.data.local.database.AppDatabase
import com.example.parcial1ppc.data.local.entity.PitStopEntity
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.first
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: PitStopDao

    @Before
    fun createDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.pitStopDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertAndGetPitStop() = runBlocking {
        val entity = PitStopEntity(
            piloto = "Lewis Hamilton",
            escuderia = "Mercedes",
            tiempoTotal = 2.5,
            cambioNeumaticos = "Soft",
            numeroNeumaticosCambiados = 4,
            estado = "Completado",
            motivoFallo = null,
            mecanicoPrincipal = "Carlos Pérez",
            fechaHora = "2025-10-21 10:00"
        )

        dao.insertPitStop(entity)

        val allItems = dao.getAllPitStops().first()
        assertEquals(1, allItems.size)
        assertEquals("Lewis Hamilton", allItems.first().piloto)
    }

    @Test
    fun deletePitStop() = runBlocking {
        val entity = PitStopEntity(
            piloto = "Max Verstappen",
            escuderia = "Red Bull",
            tiempoTotal = 2.1,
            cambioNeumaticos = "Medium",
            numeroNeumaticosCambiados = 4,
            estado = "Completado",
            motivoFallo = null,
            mecanicoPrincipal = "Carlos Pérez",
            fechaHora = "2025-10-21 12:00"
        )

        dao.insertPitStop(entity)
        val allItemsBefore = dao.getAllPitStops().first()
        assertEquals(1, allItemsBefore.size)

        dao.deletePitStopById(allItemsBefore.first().id)

        val allItemsAfter = dao.getAllPitStops().first()
        assertEquals(0, allItemsAfter.size)
    }

    @Test
    fun searchPitStops_returnsCorrectResult() = runBlocking {
        val entity1 = PitStopEntity(
            piloto = "Charles Leclerc",
            escuderia = "Ferrari",
            tiempoTotal = 2.7,
            cambioNeumaticos = "Hard",
            numeroNeumaticosCambiados = 4,
            estado = "Completado",
            motivoFallo = null,
            mecanicoPrincipal = "Carlos Pérez",
            fechaHora = "2025-10-21 14:00"
        )

        val entity2 = PitStopEntity(
            piloto = "Sergio Pérez",
            escuderia = "Red Bull",
            tiempoTotal = 3.0,
            cambioNeumaticos = "Soft",
            numeroNeumaticosCambiados = 4,
            estado = "Fallido",
            motivoFallo = "Problema con tuerca",
            mecanicoPrincipal = "Carlos Pérez",
            fechaHora = "2025-10-21 15:00"
        )

        dao.insertPitStop(entity1)
        dao.insertPitStop(entity2)

        val results = dao.searchPitStops("Red Bull").first()
        assertEquals(1, results.size)
        assertEquals("Sergio Pérez", results.first().piloto)
    }

    @Test
    fun getPitStopById_returnsCorrectEntity() = runBlocking {
        val entity = PitStopEntity(
            piloto = "Fernando Alonso",
            escuderia = "Aston Martin",
            tiempoTotal = 2.9,
            cambioNeumaticos = "Medium",
            numeroNeumaticosCambiados = 4,
            estado = "Completado",
            motivoFallo = null,
            mecanicoPrincipal = "Carlos Pérez",
            fechaHora = "2025-10-21 16:00"
        )

        dao.insertPitStop(entity)

        val inserted = dao.getAllPitStops().first().first()
        val loaded = dao.getPitStopById(inserted.id).first()
        assertEquals(inserted.piloto, loaded?.piloto)

        dao.deletePitStopById(inserted.id)
        val afterDelete = dao.getPitStopById(inserted.id).first()
        assertNull(afterDelete)
    }
}
