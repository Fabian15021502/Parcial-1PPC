
    override suspend fun deletePitStop(id: Int) {
        // Implementa requisito: Eliminar pit stop
        _pitStops.update { currentList ->
            currentList.filter { it.id != id }
        }
    }

    override fun searchPitStops(query: String): Flow<List<PitStop>> {
        // Implementa requisito: Buscar pit stop
        return _pitStops.map { list ->
            if (query.isBlank()) {
                list
            } else {
                list.filter {
                    // Búsqueda simple por Piloto o Escudería
                    it.piloto.contains(query, ignoreCase = true) ||
                            it.escuderia.contains(query, ignoreCase = true)
                }
            }
        }
    }
}