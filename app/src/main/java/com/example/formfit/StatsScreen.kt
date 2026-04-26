package com.example.formfit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Using your existing theme colors
import com.example.formfit.ui.theme.AccentBlue
import com.example.formfit.ui.theme.AccentPurple
import com.example.formfit.ui.theme.BgDark
import com.example.formfit.ui.theme.CardDark
import com.example.formfit.ui.theme.TextGrey
import com.example.formfit.ui.theme.TextWhite

@Composable
fun StatsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgDark)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        // --- Header ---
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.background(CardDark, CircleShape)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextWhite)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text("Your Statistics", color = TextWhite, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Top Quick Stats ---
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatBox(
                icon = Icons.Default.LocalFireDepartment,
                value = "5 Days",
                label = "Current Streak",
                color = Color(0xFFF59E0B),
                modifier = Modifier.weight(1f)
            )
            StatBox(
                icon = Icons.Default.FitnessCenter,
                value = "12,450",
                label = "Volume (kg)",
                color = AccentPurple,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Weekly Activity Chart (Mock Data) ---
        Text("Weekly Activity", color = TextWhite, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(CardDark)
                .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(24.dp))
                .padding(20.dp)
        ) {
            // Mock data values (0.0f to 1.0f representing intensity/completion)
            val weekData = listOf(0.6f, 0.9f, 0.4f, 1.0f, 0.0f, 0.8f, 0.5f)
            val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth().height(150.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    weekData.forEachIndexed { index, fill ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(12.dp)
                                    .fillMaxHeight(fill.coerceAtLeast(0.1f))
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(
                                        if (fill > 0f) Brush.verticalGradient(listOf(AccentBlue, AccentPurple))
                                        else Brush.verticalGradient(listOf(Color.Gray.copy(0.2f), Color.Gray.copy(0.1f)))
                                    )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(days[index], color = TextGrey, fontSize = 10.sp)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Achievements ---
        Text("Achievements", color = TextWhite, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            AchievementItem("Early Bird", "Completed 5 workouts before 8 AM", Icons.Default.EmojiEvents, true)
            AchievementItem("Iron Warrior", "Lifted a total of 50,000kg", Icons.Default.FitnessCenter, false)
        }
    }
}

@Composable
fun StatBox(
    icon: ImageVector,
    value: String,
    label: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(CardDark)
            .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(24.dp))
            .padding(16.dp)
    ) {
        Column {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(12.dp))
            Text(value, color = TextWhite, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(label, color = TextGrey, fontSize = 12.sp)
        }
    }
}

@Composable
fun AchievementItem(
    title: String,
    desc: String,
    icon: ImageVector,
    isUnlocked: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(CardDark)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(45.dp)
                .clip(CircleShape)
                .background(if (isUnlocked) AccentBlue.copy(0.1f) else Color.Gray.copy(0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = if (isUnlocked) AccentBlue else Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(title, color = TextWhite, fontWeight = FontWeight.Bold)
            Text(desc, color = TextGrey, fontSize = 12.sp)
        }
    }
}
