package com.example.parcial1ppc.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

// ====================================================================
// Componente para la fila de métricas de resumen
// ====================================================================
@Composable
fun ResumenMetrica(titulo: String, valor: String, icon: @Composable () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            icon()
            Spacer(modifier = Modifier.width(8.dp))
            Text(titulo, fontWeight = FontWeight.SemiBold)
        }
        Text(valor, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
    }
}

// ====================================================================
// Componente auxiliar para las celdas de la tabla de Listado
// ====================================================================
@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    align: Alignment.Horizontal,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = Color.Unspecified
) {
    Text(
        text = text,
        modifier = Modifier
            .weight(weight)
            .align(Alignment.CenterVertically),
        maxLines = 1,
        style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = fontWeight,
            color = color
        ),
        textAlign = when (align) {
            Alignment.Start -> TextAlign.Start
            Alignment.End -> TextAlign.End
            else -> TextAlign.Center
        }
    )
}

// ====================================================================
// Placeholder simple para el Gráfico de Barras
// ====================================================================
@Composable
fun BarChartComposable(data: List<Double>) {
    val maxTime = data.maxOrNull() ?: 1.0
    val minTime = data.minOrNull() ?: 0.0

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Bottom
    ) {
        data.forEachIndexed { _, time ->
            val normalizedTime = if (maxTime != minTime) {
                1f - ((time - minTime) / (maxTime - minTime)).toFloat()
            } else 0.5f

            val barHeight = (0.3f + normalizedTime * 0.7f).coerceIn(0.1f, 1f)

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "%.1f".format(time), style = MaterialTheme.typography.labelSmall)
                Spacer(modifier = Modifier.height(2.dp))
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .fillMaxHeight(barHeight)
                        .background(
                            Color(0xFFC92828),
                            shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                        )
                )
            }
        }
    }
    Text(
        text = "Últimos pit stops",
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodySmall
    )
}
