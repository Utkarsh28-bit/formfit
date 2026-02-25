package com.example.formfit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.Restaurant
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.Room

// --- COLOR PALETTE (Extracted from Screenshots) ---
val BgDark = Color(0xFF0B1121)
val CardDark = Color(0xFF161E2D) // Slightly lighter for cards
val TextWhite = Color(0xFFFFFFFF)
val TextGrey = Color(0xFF94A3B8)
val AccentBlue = Color(0xFF3B82F6)
val AccentPurple = Color(0xFF8B5CF6)
val AccentGreen = Color(0xFF10B981)
val AccentPink = Color(0xFFEC4899)
val DividerColor = Color(0xFF1E293B)

@Composable
fun DashboardScreen(navController: NavController) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // --- MOCK DATA FOR PREVIEW ---
    // (Replace with actual DB calls if needed, keeping it static for UI 1:1 match)
    val userName = "Utkarsh"
    val level = "Intermediate"
    val levelSub = "2y Exp"
    val proteinGoal = "150g"
    val proteinSub = "2g/kg Daily"

    // Exercise Mock List matches screenshot
    val exercises = listOf(
        Triple("Barbell Squat", "Legs • 8 reps target", Color(0xFF334155)),
        Triple("Front Squat", "Legs • 8 reps target", Color(0xFF334155)),
        Triple("Leg Extension", "Legs • 12 reps target", Color(0xFF334155)),
        Triple("Leg Curl", "Legs • 12 reps target", Color(0xFFEF4444)), // Red bg example
        Triple("Leg Press", "Legs • 10 reps target", Color(0xFF334155)),
        Triple("Standing Calf Raise", "Legs • 15 reps target", Color(0xFF334155))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgDark)
            .padding(horizontal = 20.dp)
            .verticalScroll(scrollState)
    ) {
        Spacer(Modifier.height(40.dp))

        // 1. TOP HEADER (Name + Icons)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Hi, $userName",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
                Text(
                    text = "Let's crush your goals today.",
                    fontSize = 14.sp,
                    color = TextGrey
                )
            }

            // Header Icons (Settings + Profile)
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                IconButton(
                    onClick = { /* Settings */ },
                    modifier = Modifier
                        .size(44.dp)
                        .border(1.dp, DividerColor, CircleShape)
                ) {
                    Icon(Icons.Default.Settings, null, tint = TextGrey)
                }
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(Color(0xFF1E293B), CircleShape)
                        .border(1.dp, DividerColor, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("U", color = AccentGreen, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // 2. STATS ROW (Level + Protein)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatCard(
                title = "LEVEL",
                mainValue = level,
                subValue = levelSub,
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = "PROTEIN GOAL",
                mainValue = proteinGoal,
                subValue = proteinSub,
                valueColor = AccentGreen,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(24.dp))

        // 3. HERO CARD (Today's Schedule)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF3B82F6), Color(0xFF6366F1))
                    )
                )
        ) {
            // Background Pattern (Optional opacity overlay for texture)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top Text
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Edit, // Or similar calendar icon
                            contentDescription = null,
                            tint = Color.White.copy(0.7f),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "Today's Schedule",
                            color = Color.White.copy(0.8f),
                            fontSize = 14.sp
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Leg Destruction",
                        color = Color.White,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "2 Exercises",
                        color = Color.White.copy(0.7f),
                        fontSize = 14.sp
                    )
                }

                // Button
                Button(
                    onClick = { navController.navigate("exercise/Barbell Squat") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(0.2f)
                    ),
                    shape = RoundedCornerShape(50),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp),
                    modifier = Modifier.height(36.dp)
                ) {
                    Text(
                        "START SESSION >",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // 4. ACTION GRID (Diet, Analytics, AI Studio)
        // matches Screenshot 2026-02-13 151711.png
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ActionButton(
                icon = Icons.Rounded.Restaurant,
                label = "Diet",
                tint = AccentGreen,
                modifier = Modifier.weight(1f)
            )
            ActionButton(
                icon = Icons.Rounded.BarChart,
                label = "Analytics",
                tint = AccentPurple,
                modifier = Modifier.weight(1f)
            )
            ActionButton(
                icon = Icons.Rounded.AutoAwesome,
                label = "AI Studio",
                tint = AccentPink,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(24.dp))

        // 5. EXERCISE LIBRARY HEADER
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Icon(
                // Use a book or library icon here
                imageVector = Icons.Default.List,
                contentDescription = null,
                tint = TextGrey,
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                "Exercise Library",
                color = TextWhite,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // 6. EXERCISE LIST ITEMS
        exercises.forEach { (name, subtitle, color) ->
            ExerciseListItem(name, subtitle, color) {
                navController.navigate("exercise/$name")
            }
            Spacer(Modifier.height(12.dp))
        }

        Spacer(Modifier.height(40.dp))
    }
}

// --- COMPONENT: STAT CARD ---
@Composable
fun StatCard(
    title: String,
    mainValue: String,
    subValue: String,
    valueColor: Color = TextWhite,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(100.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(title, color = TextGrey, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(4.dp))
            Text(mainValue, color = valueColor, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(subValue, color = TextGrey, fontSize = 12.sp)
        }
    }
}

// --- COMPONENT: ACTION BUTTON (Middle Row) ---
@Composable
fun ActionButton(
    icon: ImageVector,
    label: String,
    tint: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(100.dp).clickable { /* Action */ },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardDark)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(28.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(label, color = TextWhite, fontSize = 13.sp)
        }
    }
}

// --- COMPONENT: EXERCISE LIST ITEM ---
@Composable
fun ExerciseListItem(
    title: String,
    subtitle: String,
    boxColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardDark)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon Placeholder Box
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(boxColor.copy(alpha = 0.3f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                // You can put a small image here later
            }

            Spacer(Modifier.width(16.dp))

            // Text Content
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = TextWhite,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
                Text(
                    text = subtitle,
                    color = TextGrey,
                    fontSize = 12.sp
                )
            }

            // Arrow
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = TextGrey
            )
        }
    }
}