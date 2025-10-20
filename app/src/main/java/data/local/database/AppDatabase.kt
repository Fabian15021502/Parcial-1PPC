package com.example.parcial1ppc.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.parcial1ppc.data.local.dao.PitStopDao
import com.example.parcial1ppc.data.local.entity.PitStopEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [PitStopEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pitStopDao(): PitStopDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pit_stop_db"
                ).addCallback(PitStopDatabaseCallback())
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    // Callback para insertar los datos de ejemplo del mockup al crear la DB
    private class PitStopDatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    val dao = database.pitStopDao()

                    // Datos de ejemplo del mockup (para que la app inicie con datos)
                    dao.insert(PitStopEntity(1, "Oliveiro", "Mercedes", 2.4, "Soft", 4, "Ok", null, "John Doe", "12/05/2024 14:30"))
                    dao.insert(PitStopEntity(2, "James", "Ferrari", 2.8, "Medium", 4, "Fallido", "Fallo tuerca", "Jane Smith", "12/05/2024 14:40"))
                    dao.insert(PitStopEntity(3, "Mark", "Red Bull", 2.3, "Hard", 4, "Ok", null, "Max B.", "12/05/2024 14:50"))
                    dao.insert(PitStopEntity(4, "Sebastian", "Aston Martin", 3.1, "Soft", 4, "Fallido", "Error gato", "Peter K.", "12/05/2024 15:00"))
                    dao.insert(PitStopEntity(5, "Lucas", "McLaren", 3.0, "Medium", 4, "Fallido", "Neumático duro", "Ana C.", "12/05/2024 15:10"))
                }
            }
        }
    }
}