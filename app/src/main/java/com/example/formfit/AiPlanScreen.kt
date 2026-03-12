package com.example.formfit

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.formfit.ui.theme.*

@Composable
fun AiPlanScreen() {
    val context = LocalContext.current
    var profile by remember { mutableStateOf<ProfileEntity?>(null) }
    var generatedPrompt by remember { mutableStateOf("Loading your profile to initialize FormFit AI...") }

    // Fetch the profile from the database
    LaunchedEffect(Unit) {
        val db = AppDatabase.getDatabase(context)
        profile = db.profileDao().getProfile()

        // Generate the prompt once the profile is loaded
        profile?.let {
            generatedPrompt = generateAiPrompt(it, "Muscle Gain")
        }
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
        Text("FormFit Logic AI 🧠", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = TextWhite)
        Text("Generative AI Engine", color = AccentPurple, fontSize = 16.sp)

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { /* TODO: Trigger Gemini API Call Here */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AccentBlue)
        ) {
            Text("Generate Smart Plan", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Glassmorphism Card displaying the payload
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(CardDark)
                .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column {
                Text("API Payload Preview:", color = AccentBlue, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = generatedPrompt,
                    color = TextGrey,
                    fontSize = 13.sp,
                    lineHeight = 20.sp,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                )
            }
        }
    }
}

// Generates the strict structured prompt for the AI
fun generateAiPrompt(profile: ProfileEntity, goal: String): String {
    return """
        You are 'FormFit AI', an elite personal trainer and clinical nutritionist.
        Your task is to generate a highly customized, 1-day sample fitness and diet plan based strictly on the user's profile.
        
        USER PROFILE:
        - Name: ${profile.name}
        - Age: ${profile.age}
        - Height: ${profile.height} cm
        - Weight: ${profile.weight} kg
        - Fitness Level: ${profile.experience}
        - Dietary Preference: Pure Vegetarian
        - Medical/Allergies: ${profile.allergy} (Must strictly avoid this)
        - Primary Goal: $goal

        RULES:
        1. Do not include any introductory or concluding text.
        2. Ensure all meals strictly respect the dietary preferences and allergies.
        3. Provide the response strictly in the following JSON format.
        
        EXPECTED JSON FORMAT:
        {
          "workout_focus": "Name of the muscle group or focus",
          "exercises": [
            { "name": "Exercise Name", "sets_reps": "Sets x Reps" }
          ],
          "diet_focus": "Brief explanation",
          "meals": {
            "breakfast": "Detailed breakfast description",
            "lunch": "Detailed lunch description",
            "dinner": "Detailed dinner description"
          }
        }
    """.trimIndent()
}