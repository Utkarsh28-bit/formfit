package com.example.formfit

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProgressionChartScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White)
    ) {
        Text(
            text = "Fig 4.4 Analytics Progression Chart",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "University Workout Progress (Weeks 1-10)",
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Data Points matching the image exactly
        val weightData = listOf(60f, 61.1f, 62.3f, 63.4f, 64.6f, 65.5f, 66.8f, 68.0f, 69.1f, 70f)
        val volumeData = listOf(1440f, 1520f, 1610f, 1720f, 1850f, 1350f, 1910f, 1990f, 2070f, 2100f)

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            val width = size.width
            val height = size.height
            val xStep = width / (weightData.size - 1)

            // Grid Lines
            val gridColor = Color.LightGray
            for (i in 0..10) { // Vertical lines
                val x = i * (width / 10)
                drawLine(gridColor, start = Offset(x, 0f), end = Offset(x, height), strokeWidth = 1f)
            }
            for (i in 0..7) { // Horizontal lines
                val y = i * (height / 7)
                drawLine(gridColor, start = Offset(0f, y), end = Offset(width, y), strokeWidth = 1f)
            }

            // Draw Weight Line (Solid Black)
            val weightPath = Path()
            val minWeight = 58f
            val maxWeight = 72f
            val weightRange = maxWeight - minWeight

            weightData.forEachIndexed { index, weight ->
                val x = index * xStep
                // Map the weight to the canvas height
                val y = height - ((weight - minWeight) / weightRange * height)

                if (index == 0) weightPath.moveTo(x, y) else weightPath.lineTo(x, y)
                drawCircle(color = Color.Black, radius = 4.dp.toPx(), center = Offset(x, y))
            }
            drawPath(path = weightPath, color = Color.Black, style = Stroke(width = 3.dp.toPx()))

            // Draw Volume Line (Dashed Black)
            val volumePath = Path()
            val minVolume = 1300f
            val maxVolume = 2200f
            val volumeRange = maxVolume - minVolume

            volumeData.forEachIndexed { index, volume ->
                val x = index * xStep
                val y = height - ((volume - minVolume) / volumeRange * height)

                if (index == 0) volumePath.moveTo(x, y) else volumePath.lineTo(x, y)
                drawCircle(color = Color.Black, radius = 4.dp.toPx(), center = Offset(x, y))
            }
            drawPath(
                path = volumePath,
                color = Color.Black,
                style = Stroke(
                    width = 2.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                )
            )
        }

        // Legend Mockup
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.width(20.dp).height(2.dp).background(Color.Black))
            Text(" Weight (kg)", modifier = Modifier.padding(end = 16.dp))

            Text("---", fontWeight = FontWeight.Bold)
            Text(" Volume")
        }
    }
}