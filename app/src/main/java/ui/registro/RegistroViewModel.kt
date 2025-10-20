// Paquete: com.example.parcial1ppc.data.repository

interface IPitStopRepository {
    // Retorna un flujo (Flow) de la lista de PitStop (se actualiza automáticamente)
    fun getAllPitStops(): Flow<List<PitStop>>

    // Retorna un flujo de la lista filtrada
    fun searchPitStops(query: String): Flow<List<PitStop>>

    // Guarda o actualiza el PitStop
    suspend fun savePitStop(pitStop: PitStop)

    // Elimina el PitStop por su ID
    suspend fun deletePitStop(id:Int)
}