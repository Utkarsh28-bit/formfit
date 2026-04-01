package com.example.formfit

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.formfit.ui.theme.AccentBlue
import com.example.formfit.ui.theme.AccentPurple

@Composable
fun BmiChart(data: List<Float>, modifier: Modifier = Modifier) {
    // We need at least two points to draw a line!
    if (data.size < 2) return

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val maxVal = data.maxOrNull() ?: 0f
        val minVal = data.minOrNull() ?: 0f
        val range = (maxVal - minVal).coerceAtLeast(1f) // Avoid division by zero

        val points = data.mapIndexed { index, value ->
            Offset(
                x = index * (width / (data.size - 1)),
                y = height - ((value - minVal) / range * height * 0.8f) - (height * 0.1f)
            )
        }

        // 1. Draw the Gradient Fill
        val fillPath = Path().apply {
            moveTo(points.first().x, height)
            points.forEach { lineTo(it.x, it.y) }
            lineTo(points.last().x, height)
            close()
        }

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(AccentBlue.copy(alpha = 0.3f), Color.Transparent)
            )
        )

        // 2. Draw the Glowing Line
        val strokePath = Path().apply {
            moveTo(points.first().x, points.first().y)
            points.forEach { lineTo(it.x, it.y) }
        }

        drawPath(
            path = strokePath,
            color = AccentBlue,
            style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        )

        // 3. Draw the Last Point (Current BMI)
        drawCircle(
            color = Color.White,
            radius = 6.dp.toPx(),
            center = points.last()
        )
    }
}