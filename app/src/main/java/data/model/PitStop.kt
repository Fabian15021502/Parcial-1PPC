package com.example.parcial1ppc.data.model

// El modelo de datos completo basado en el mockup de registro.
// id es nullable para soportar insert (id null -> Room autogenera) y edición (id != null -> update)
data class PitStop(
    val id: Int? = null,
    val piloto: String,
    val escuderia: String,
    val tiempoTotal: Double, // Usado para cálculos de resumen
    val cambioNeumaticos: String,
    val numeroNeumaticosCambiados: Int,
    val estado: String, // 'Ok' o 'Fallido'
    val motivoFallo: String?,
    val mecanicoPrincipal: String,
    val fechaHora: String
)
