
// El modelo de datos completo basado en el mockup de registro.
data class PitStop(
    val id: Int,
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