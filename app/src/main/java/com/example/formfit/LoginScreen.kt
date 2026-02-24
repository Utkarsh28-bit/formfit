package com.example.formfit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext
import androidx.room.Room
import kotlinx.coroutines.launch
import kotlin.math.pow

@Composable
fun LoginScreen(navController: NavController) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var allergy by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val heightOptions = (140..210).map { "$it cm" }
    val weightOptions = (40..150).map { "$it kg" }
    val ageOptions = (10..80).map { "$it" }

    var selectedHeight by remember { mutableStateOf("170 cm") }
    var selectedWeight by remember { mutableStateOf("70 kg") }
    var selectedAge by remember { mutableStateOf("25") }

    val experienceOptions = listOf("Beginner", "Intermediate", "Advanced")
    var selectedExperience by remember { mutableStateOf("Beginner") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF0B1423),
                        Color(0xFF0F1B2E),
                        Color(0xFF132236)
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                "Create Your Profile",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(Modifier.height(30.dp))

            GlassCard {

                CustomField(name, { name = it }, "Name")

                Spacer(Modifier.height(12.dp))

                CustomField(email, { email = it }, "Email")

                Spacer(Modifier.height(12.dp))

                // 🔐 PASSWORD BELOW EMAIL
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    singleLine = true,
                    visualTransformation =
                        if (passwordVisible) VisualTransformation.None
                        else PasswordVisualTransformation(),
                    trailingIcon = {
                        Text(
                            text = if (passwordVisible) "Hide" else "Show",
                            modifier = Modifier.clickable {
                                passwordVisible = !passwordVisible
                            },
                            color = Color(0xFF00E5A0),
                            fontSize = 12.sp
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF00E5A0),
                        unfocusedBorderColor = Color(0xFF2A3A4A),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = Color(0xFF00E5A0),
                        unfocusedLabelColor = Color.Gray,
                        cursorColor = Color(0xFF00E5A0),
                        focusedContainerColor = Color(0xFF132236),
                        unfocusedContainerColor = Color(0xFF132236)
                    )
                )

                Spacer(Modifier.height(12.dp))

                CustomField(allergy, { allergy = it }, "Allergy")

                Spacer(Modifier.height(16.dp))

                DropdownSelector("Height", heightOptions, selectedHeight) {
                    selectedHeight = it
                }

                Spacer(Modifier.height(12.dp))

                DropdownSelector("Weight", weightOptions, selectedWeight) {
                    selectedWeight = it
                }

                Spacer(Modifier.height(12.dp))

                DropdownSelector("Age", ageOptions, selectedAge) {
                    selectedAge = it
                }

                Spacer(Modifier.height(20.dp))

                Text(
                    "Experience Level",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    experienceOptions.forEach { level ->

                        val selected = level == selectedExperience

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(
                                    if (selected) Color(0xFF00E5A0)
                                    else Color(0xFF1E2A3A)
                                )
                                .clickable { selectedExperience = level }
                                .padding(horizontal = 20.dp, vertical = 10.dp)
                        ) {
                            Text(
                                level,
                                color = if (selected) Color.Black else Color.White
                            )
                        }
                    }
                }

                Spacer(Modifier.height(30.dp))

                Button(
                    onClick = {

                        val heightCm = selectedHeight.replace(" cm", "").toFloat()
                        val weightKg = selectedWeight.replace(" kg", "").toFloat()
                        val ageValue = selectedAge.toInt()

                        val heightMeters = heightCm / 100f
                        val bmi = weightKg / heightMeters.pow(2)

                        val ageCategory = when {
                            ageValue < 18 -> "Teen"
                            ageValue < 30 -> "Young Adult"
                            ageValue < 50 -> "Adult"
                            else -> "Senior"
                        }

                        val profile = ProfileEntity(
                            name = name,
                            height = heightCm,
                            weight = weightKg,
                            age = ageValue,
                            ageCategory = ageCategory,
                            experience = selectedExperience,
                            allergy = allergy,
                            email = email,
                            password = password,
                            bmi = bmi
                        )

                        val db = Room.databaseBuilder(
                            context,
                            AppDatabase::class.java,
                            "fitness_db"
                        )
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build()

                        scope.launch {
                            db.profileDao().insertProfile(profile)
                            navController.navigate("dashboard")
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00E5A0)
                    )
                ) {
                    Text(
                        "Continue",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        }
    }
}
