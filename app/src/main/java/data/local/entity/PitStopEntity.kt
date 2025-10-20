package com.example.parcial1ppc.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.parcial1ppc.data.model.PitStop

@Entity(tableName = "pit_stops")
data class PitStopEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val piloto: String,
    val escuderia: String,
    val tiempoTotal: Double,
    val cambioNeumaticos: String,
    val numeroNeumaticosCambiados: Int,
    val estado: String,
    val motivoFallo: String?,
    val mecanicoPrincipal: String,
    val fechaHora: String
)

// Extensiones para convertir entre PitStopEntity y PitStop
fun PitStopEntity.toDomain(): PitStop {
    return PitStop(
        id = if (this.id <= 0) null else this.id,
        piloto = this.piloto,
        escuderia = this.escuderia,
        tiempoTotal = this.tiempoTotal,
        cambioNeumaticos = this.cambioNeumaticos,
        numeroNeumaticosCambiados = this.numeroNeumaticosCambiados,
        estado = this.estado,
        motivoFallo = this.motivoFallo,
        mecanicoPrincipal = this.mecanicoPrincipal,
        fechaHora = this.fechaHora
    )
}

fun PitStop.toEntity(): PitStopEntity {
    return PitStopEntity(
        id = this.id ?: 0, // Room autogenera si 0
        piloto = this.piloto,
        escuderia = this.escuderia,
        tiempoTotal = this.tiempoTotal,
        cambioNeumaticos = this.cambioNeumaticos,
        numeroNeumaticosCambiados = this.numeroNeumaticosCambiados,
        estado = this.estado,
        motivoFallo = this.motivoFallo,
        mecanicoPrincipal = this.mecanicoPrincipal,
        fechaHora = this.fechaHora
    )
}
