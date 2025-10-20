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
        private var _INSTANCE: AppDatabase? = null    // privada

        // ✅ Solo lectura pública
        val INSTANCE: AppDatabase?
            get() = _INSTANCE

        fun getDatabase(context: Context): AppDatabase {
            return _INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pit_stop_db"
                )
                    .addCallback(PitStopCallback())
                    .fallbackToDestructiveMigration()
                    .build()
                _INSTANCE = instance
                instance
            }
        }
    }
}

class PitStopCallback : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        AppDatabase.INSTANCE?.let { database ->
            CoroutineScope(Dispatchers.IO).launch {
                val dao = database.pitStopDao()

                dao.insertPitStop(PitStopEntity(0, "Oliveiro", "Mercedes", 2.4, "Soft", 4, "Ok", null, "John Doe", "12/05/2024 14:30"))
                dao.insertPitStop(PitStopEntity(0, "James", "Ferrari", 2.8, "Medium", 4, "Fallido", "Fallo tuerca", "Jane Smith", "12/05/2024 14:40"))
                dao.insertPitStop(PitStopEntity(0, "Mark", "Red Bull", 2.3, "Hard", 4, "Ok", null, "Max B.", "12/05/2024 14:50"))
                dao.insertPitStop(PitStopEntity(0, "Sebastian", "Aston Martin", 3.1, "Soft", 4, "Fallido", "Error gato", "Peter K.", "12/05/2024 15:00"))
                dao.insertPitStop(PitStopEntity(0, "Lucas", "McLaren", 3.0, "Medium", 4, "Fallido", "Neumático duro", "Ana C.", "12/05/2024 15:10"))
            }
        }
    }
}
