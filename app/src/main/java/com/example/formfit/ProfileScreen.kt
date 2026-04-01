package com.example.formfit

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.formfit.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val db = remember { AppDatabase.getDatabase(context) }

    // State variables for our form fields
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("Beginner") }
    var allergy by remember { mutableStateOf("") }

    // Load existing data when the screen opens
    LaunchedEffect(Unit) {
        val existingProfile = db.profileDao().getProfile()
        if (existingProfile != null) {
            name = existingProfile.name
            age = existingProfile.age.toString()
            weight = existingProfile.weight.toString()
            height = existingProfile.height.toString()
            experience = existingProfile.experience
            allergy = existingProfile.allergy
        }
    }

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
            Text("Edit Profile", color = TextWhite, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(32.dp))

        // --- Avatar ---
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Brush.linearGradient(listOf(AccentBlue, AccentPurple)))
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(50.dp))
        }

        Spacer(modifier = Modifier.height(32.dp))

        // --- Form Fields ---
        GlassTextField(value = name, onValueChange = { name = it }, label = "Full Name")
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            GlassTextField(value = age, onValueChange = { age = it }, label = "Age", isNumber = true, modifier = Modifier.weight(1f))
            GlassTextField(value = weight, onValueChange = { weight = it }, label = "Weight (kg)", isNumber = true, modifier = Modifier.weight(1f))
        }
        GlassTextField(value = height, onValueChange = { height = it }, label = "Height (cm)", isNumber = true)
        GlassTextField(value = allergy, onValueChange = { allergy = it }, label = "Food Allergies (Optional)")

        Spacer(modifier = Modifier.height(16.dp))
        Text("Experience Level", color = TextWhite, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            listOf("Beginner", "Intermediate", "Advanced").forEach { level ->
                GoalSelector(
                    text = level,
                    isSelected = experience == level,
                    modifier = Modifier.weight(1f)
                ) { experience = it }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // --- Save Button ---
        Button(
            onClick = {
                val w = weight.toFloatOrNull() ?: 0f
                val h = height.toFloatOrNull() ?: 0f
                // Calculate new BMI
                val newBmi = if (h > 0) w / ((h / 100) * (h / 100)) else 0f

                val updatedProfile = ProfileEntity(
                    name = name,
                    age = age.toIntOrNull() ?: 0,
                    weight = w,
                    height = h,
                    bmi = newBmi,
                    experience = experience,
                    allergy = allergy
                )

                coroutineScope.launch {
                    db.profileDao().insertProfile(updatedProfile)
                    Toast.makeText(context, "Profile Updated Successfully!", Toast.LENGTH_SHORT).show()
                    navController.navigate("dashboard") {
                        popUpTo("dashboard") { inclusive = true } // Refresh dashboard
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AccentBlue)
        ) {
            Text("Save & Update Stats", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

// --- HELPER COMPONENT FOR PROFILE ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlassTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isNumber: Boolean = false,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = TextGrey) },
        keyboardOptions = KeyboardOptions(keyboardType = if (isNumber) KeyboardType.Number else KeyboardType.Text),
        modifier = modifier.fillMaxWidth().padding(bottom = 16.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = AccentBlue,
            unfocusedBorderColor = Color.White.copy(alpha = 0.1f),
            focusedTextColor = TextWhite,
            unfocusedTextColor = TextWhite,
            containerColor = CardDark
        ),
        shape = RoundedCornerShape(16.dp)
    )
}