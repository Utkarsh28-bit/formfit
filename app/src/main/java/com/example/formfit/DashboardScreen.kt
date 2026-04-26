package com.example.formfit

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.formfit.viewmodel.ProfileViewModel
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.formfit.utils.getBmiDetails

// ✅ Master Colors Imported from your Theme
import com.example.formfit.ui.theme.AccentBlue
import com.example.formfit.ui.theme.AccentPurple
import com.example.formfit.ui.theme.BgDark
import com.example.formfit.ui.theme.CardDark
import com.example.formfit.ui.theme.TextGrey
import com.example.formfit.ui.theme.TextWhite

// ✅ Gradients
val GlassGradient = Brush.linearGradient(colors = listOf(AccentBlue.copy(alpha = 0.8f), AccentPurple.copy(alpha = 0.8f)))
val SelectedTabGradient = Brush.linearGradient(colors = listOf(AccentBlue, AccentPurple))

@Composable
fun DashboardScreen(navController: NavController, profileViewModel: ProfileViewModel = viewModel()) {
    val context = LocalContext.current
    val profile by profileViewModel.profileState.collectAsState()
    var selectedSplit by remember { mutableStateOf("Push") }

    LaunchedEffect(Unit) {
        profileViewModel.fetchProfile()
    }

    val currentExercises = exerciseList.filter { it.category == selectedSplit }

    Column(modifier = Modifier.fillMaxSize().background(BgDark).verticalScroll(rememberScrollState()).padding(20.dp)) {
        Spacer(Modifier.height(20.dp))

        // Header
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text("Hi, ${profile?.name ?: "User"} 👋", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = TextWhite)
                Text("Let's crush your goals.", color = TextGrey)
            }
            Box(modifier = Modifier.size(45.dp).clip(CircleShape).background(CardDark).border(1.dp, TextGrey.copy(0.3f), CircleShape).clickable { navController.navigate("profile") }, contentAlignment = Alignment.Center) {
                Text(profile?.name?.take(1) ?: "U", color = AccentBlue, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(Modifier.height(24.dp))

        // Stats Row
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            DashboardCard("AGE", "${profile?.age ?: "--"}", "Years Old", Modifier.weight(1f))
            DashboardCard("LEVEL", profile?.experience ?: "--", "Experience", Modifier.weight(1f))
        }

        Spacer(Modifier.height(24.dp))

        // ✅ FIXED: New BMI Hero Glass Card with the Progress Chart
        val bmi = profile?.bmi ?: 0f
        val (_, bmiText, _) = getBmiDetails(bmi)

        GlassCard(modifier = Modifier.fillMaxWidth().height(180.dp), gradient = GlassGradient) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left Side: Text Info
                Column(modifier = Modifier.weight(1f)) {
                    Text("Health Status", color = TextWhite.copy(alpha = 0.8f), fontSize = 14.sp)
                    Text(String.format("%.1f", bmi), color = TextWhite, fontSize = 36.sp, fontWeight = FontWeight.Bold)
                    Text("BMI: $bmiText", color = TextWhite.copy(alpha = 0.9f), fontSize = 18.sp)
                }

                // Right Side: The Progress Chart
                val mockHistory = listOf(26.5f, 25.8f, 26.2f, 25.4f, bmi)

                BmiChart(
                    data = mockHistory,
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp)
                        .padding(start = 16.dp)
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        // Action Buttons Row
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ActionButton(Icons.Default.List, "Diet", Color(0xFF10B981), Modifier.weight(1f)) { navController.navigate("diet") }

            // ✅ FIX: Added the navigation route for Stats!
            ActionButton(Icons.Default.Info, "Stats", Color(0xFF8B5CF6), Modifier.weight(1f)) { navController.navigate("stats") }

            ActionButton(Icons.Default.Star, "AI Plan", Color(0xFFEC4899), Modifier.weight(1f)) { navController.navigate("ai_plan") }
        }

        Spacer(Modifier.height(24.dp))

        // Workout Schedule
        Text("Workout Schedule", color = TextWhite, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            listOf("Push", "Pull", "Legs").forEach { split ->
                val isSelected = selectedSplit == split
                GlassCard(
                    modifier = Modifier.weight(1f).height(100.dp).clickable { selectedSplit = split },
                    gradient = if (isSelected) SelectedTabGradient else null
                ) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(split, color = TextWhite, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // Exercise List
        Text("Exercises", color = TextWhite, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))

        currentExercises.forEach { exercise ->
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
                    .clickable {
                        navController.navigate("exercise/${exercise.name}")
                    }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    // Standard Image to use local drawables
                    Image(
                        painter = painterResource(id = exercise.imageRes),
                        contentDescription = exercise.name,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(CardDark),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(exercise.name, color = TextWhite, fontWeight = FontWeight.Bold)
                        Text("${exercise.reps} reps • ${exercise.muscles}", color = TextGrey, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

// --- REUSABLE UI COMPONENTS ---
@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    gradient: Brush? = null,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(gradient ?: Brush.linearGradient(listOf(CardDark, CardDark)))
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(Color.White.copy(alpha = 0.15f), Color.White.copy(alpha = 0.05f))
                ),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(16.dp)
    ) {
        content()
    }
}

@Composable
fun DashboardCard(
    title: String,
    value: String,
    sub: String,
    modifier: Modifier = Modifier
) {
    GlassCard(modifier = modifier.height(100.dp)) {
        Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
            Text(title, color = TextGrey, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Text(value, color = TextWhite, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(sub, color = TextGrey, fontSize = 12.sp)
        }
    }
}

@Composable
fun ActionButton(
    icon: ImageVector,
    label: String,
    tint: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    GlassCard(
        modifier = modifier.height(90.dp).clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = label, tint = tint, modifier = Modifier.size(24.dp))
            Spacer(Modifier.height(4.dp))
            Text(label, color = TextWhite, fontSize = 12.sp)
        }
    }
}