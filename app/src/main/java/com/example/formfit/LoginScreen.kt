package com.example.formfit

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.*
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext
import androidx.room.Room
import kotlinx.coroutines.launch
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScrollDropdown(
    label: String,
    options: List<String>,
    selectedValue: String,
    onValueChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {

        OutlinedTextField(
            value = selectedValue,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onValueChange(item)
                        expanded = false
                    }
                )
            }
        }
    }
}
@Composable
fun LoginScreen(navController: NavController) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var allergy by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // ✅ Create DB only once
    val db = remember {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "fitness_db"
        )
            .allowMainThreadQueries()
            .build()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B1423))
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {

        Text(
            "Create Profile 💪",
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        CustomField(name, { name = it }, "Name")
        val heightOptions = (140..210).map { "$it cm" }
        val weightOptions = (40..150).map { "$it kg" }
        val ageOptions = (10..80).map { "$it" }

        var selectedHeight by remember { mutableStateOf("170 cm") }
        var selectedWeight by remember { mutableStateOf("70 kg") }
        var selectedAge by remember { mutableStateOf("25") }

        ScrollDropdown("Height", heightOptions, selectedHeight) {
            selectedHeight = it
        }

        Spacer(Modifier.height(12.dp))

        ScrollDropdown("Weight", weightOptions, selectedWeight) {
            selectedWeight = it
        }

        Spacer(Modifier.height(12.dp))

        ScrollDropdown("Age", ageOptions, selectedAge) {
            selectedAge = it
        }
        CustomField(allergy, { allergy = it }, "Allergy")
        CustomField(email, { email = it }, "Email")

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", color = Color.LightGray) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {

                // ✅ Validation
                val heightCm = selectedHeight.replace(" cm", "").toFloat()
                val weightKg = selectedWeight.replace(" kg", "").toFloat()
                val ageValue = selectedAge.toInt()

                val heightMeters = heightCm / 100f
                val bmi = weightKg / (heightMeters * heightMeters)

                val ageCategory = getAgeCategory(ageValue)

                val profile = ProfileEntity(
                    name = name,
                    height = heightCm,
                    weight = weightKg,
                    age = ageValue,
                    ageCategory = ageCategory,
                    allergy = allergy,
                    email = email,
                    bmi = bmi
                )

                scope.launch {
                    db.profileDao().insertProfile(profile)

                    navController.navigate("dashboard") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continue")
        }
    }
}
