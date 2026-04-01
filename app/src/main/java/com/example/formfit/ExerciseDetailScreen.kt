package com.example.formfit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.formfit.ui.theme.*

@Composable
fun ExerciseDetailScreen(exercise: Exercise) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgDark)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        // 1. Header (Name & Category Tag)
        Text(
            text = exercise.name,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = TextWhite
        )

        Box(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(AccentBlue.copy(alpha = 0.2f))
                .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
            Text(exercise.category.uppercase(), color = AccentBlue, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ✅ 2. Main Exercise Media Container (Video or Image)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp) // Large prominent area
                .clip(RoundedCornerShape(24.dp))
                .background(CardDark)
                .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(24.dp)),
            contentAlignment = Alignment.Center
        ) {
            // Check if this exercise has a video resource attached
            if (exercise.videoRes != null) {
                VideoPlayer(videoResId = exercise.videoRes)
            } else {
                // Fallback to Image if no video is provided
                Image(
                    painter = painterResource(id = exercise.imageRes),
                    contentDescription = "${exercise.name} demonstration",
                    modifier = Modifier.fillMaxSize().padding(8.dp).clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Fit
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 3. Info Cards (Reps & Target Muscles)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            DetailInfoCard("Reps/Sets", exercise.reps, Modifier.weight(1f))
            DetailInfoCard("Target Muscles", exercise.muscles, Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 4. Execution Steps
        Text("Execution Steps", color = TextWhite, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        exercise.steps.forEachIndexed { index, step ->
            StepItem(index + 1, step)
            Spacer(modifier = Modifier.height(12.dp))
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

// --- HELPER COMPONENTS ---

@Composable
fun DetailInfoCard(label: String, value: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(CardDark)
            .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(label, color = TextGrey, fontSize = 12.sp)
            Text(value, color = TextWhite, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun StepItem(number: Int, instruction: String) {
    Row(verticalAlignment = Alignment.Top) {
        Text(
            text = "$number.",
            color = AccentPurple,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.width(28.dp)
        )
        Text(
            text = instruction,
            color = TextWhite.copy(alpha = 0.9f),
            fontSize = 16.sp,
            lineHeight = 22.sp
        )
    }
}