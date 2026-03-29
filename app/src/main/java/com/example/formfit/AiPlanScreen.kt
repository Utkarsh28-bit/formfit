package com.example.formfit

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.formfit.ui.theme.*

@Composable
fun AiPlanScreen() {
    val context = LocalContext.current
    var profile by remember { mutableStateOf<ProfileEntity?>(null) }

    // UI States for the AI Generation
    var isGenerating by remember { mutableStateOf(false) }
    var generatedPlan by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    // Fetch the profile from your Room Database
    LaunchedEffect(Unit) {
        val db = AppDatabase.getDatabase(context)
        profile = db.profileDao().getProfile()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgDark)
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text("FormFit AI 🧠", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = TextWhite)
        Text("Generative Intelligence Engine", color = AccentPurple, fontSize = 16.sp)

        Spacer(modifier = Modifier.height(32.dp))

        // The Generate Button
        Button(
            onClick = {
                coroutineScope.launch {
                    isGenerating = true
                    generatedPlan = null
                    delay(2500) // Fakes the network latency of a real Gen-AI API call
                    generatedPlan = generateMockAiResponse(profile)
                    isGenerating = false
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AccentBlue),
            enabled = !isGenerating && profile != null
        ) {
            if (isGenerating) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text("Analyzing Profile & Generating...", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            } else {
                Icon(Icons.Default.AutoAwesome, contentDescription = "AI")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Generate Smart Plan", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Display the Resulting Plan
        if (generatedPlan != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(CardDark)
                    .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(24.dp))
                    .padding(20.dp)
            ) {
                Column {
                    Text("✅ Success: Plan Generated", color = Color(0xFF10B981), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = generatedPlan!!,
                        color = TextWhite.copy(alpha = 0.9f),
                        fontSize = 15.sp,
                        lineHeight = 24.sp
                    )
                }
            }
        } else if (!isGenerating && profile != null) {
            Text(
                "Ready to analyze data for ${profile?.name}. Tap the button above to generate a custom day plan.",
                color = TextGrey,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }
    }
}

// Fakes the AI output, tailoring it specifically to your database profile
fun generateMockAiResponse(profile: ProfileEntity?): String {
    val name = profile?.name ?: "User"
    val allergyText = if (profile?.allergy?.isNotBlank() == true) {
        "• Allergen Alert: Strictly avoided ${profile.allergy}.\n"
    } else ""

    return """
        TARGET: Muscle Gain | LEVEL: ${profile?.experience ?: "Intermediate"}
        
        Based on your profile ($name, ${profile?.age} yrs, ${profile?.weight}kg), here is your optimized daily protocol:
        
        $allergyText• Diet Type: Pure Vegetarian

        🏋️ WORKOUT FOCUS: Hypertrophy (Push Focus)
        1. Incline Dumbbell Press: 4 sets x 8-10 reps (Focus on upper chest stretch)
        2. Overhead Shoulder Press: 3 sets x 10 reps
        3. Cable Chest Fly: 3 sets x 15 reps (Squeeze at the peak)
        4. Tricep Extensions: 3 sets x 12 reps
        
        🥗 NUTRITION STRATEGY (100% Dairy-Free)
        Breakfast: 
        Tofu Scramble (150g) with 2 slices whole-wheat toast. 1 cup Almond Milk. 
        (Est. 28g Protein)
        
        Lunch: 
        Soya Chunk Curry (75g dry) with 1 cup brown rice and a large cucumber salad. 
        (Est. 35g Protein)
        
        Dinner: 
        Moong Dal Chilla (3 pieces) with mint chutney and roasted veggies.
        (Est. 22g Protein)
        
        💡 AI Coach Tip: 
        Since your goal is muscle gain at ${profile?.weight}kg, aim to consume at least 3 liters of water today to aid in cell volumization!
    """.trimIndent()
}