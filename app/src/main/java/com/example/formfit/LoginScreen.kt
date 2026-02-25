package com.example.formfit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun FormFitConfigScreen(navController: NavController) {
    // State Variables
    var displayName by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("180") }
    var weight by remember { mutableStateOf("75") }
    var experience by remember { mutableStateOf("0") }

    // Dropdown selections
    var dietPreference by remember { mutableStateOf("Vegetarian") }
    var foodAllergies by remember { mutableStateOf("None") }

    // Colors extracted from screenshot
    val BackgroundColor = Color(0xFF0F172A) // Deep Navy/Slate
    val CardBackgroundColor = Color(0xFF1E293B) // Lighter Slate for inputs
    val AccentBlue = Color(0xFF2563EB) // Royal Blue Button
    val TextLabelColor = Color(0xFF94A3B8) // Light Grey for labels
    val TitleBlue = Color(0xFF38BDF8) // Light Blue for "Logic"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // --- HEADER ---
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "FormFit ",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF60A5FA) // Light Blue
                    )
                )
                Text(
                    text = "Logic",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = TitleBlue
                    )
                )
            }

            Text(
                text = "Configure your digital brain.",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = TextLabelColor
                ),
                modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
            )

            // --- FORM FIELDS ---

            // Display Name
            ConfigLabel("Display Name")
            ConfigTextField(
                value = displayName,
                onValueChange = { displayName = it },
                placeholder = "e.g. Alex",
                backgroundColor = CardBackgroundColor
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Height & Weight Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    ConfigLabel("Height (cm)")
                    ConfigTextField(
                        value = height,
                        onValueChange = { height = it },
                        placeholder = "180",
                        keyboardType = KeyboardType.Number,
                        backgroundColor = CardBackgroundColor
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    ConfigLabel("Weight (kg)")
                    ConfigTextField(
                        value = weight,
                        onValueChange = { weight = it },
                        placeholder = "75",
                        keyboardType = KeyboardType.Number,
                        backgroundColor = CardBackgroundColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Experience
            ConfigLabel("Experience (Years)")
            ConfigTextField(
                value = experience,
                onValueChange = { experience = it },
                placeholder = "0",
                keyboardType = KeyboardType.Number,
                backgroundColor = CardBackgroundColor
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Dietary Preference (Dropdown)
            ConfigLabel("Dietary Preference")
            ConfigDropdown(
                options = listOf("Vegetarian", "Vegan", "Keto", "Paleo", "None"),
                selected = dietPreference,
                onSelect = { dietPreference = it },
                backgroundColor = CardBackgroundColor
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Food Allergies (Dropdown)
            ConfigLabel("Food Allergies")
            ConfigDropdown(
                options = listOf("None", "Milk", "Peanuts", "Shellfish", "Gluten"),
                selected = foodAllergies,
                onSelect = { foodAllergies = it },
                backgroundColor = CardBackgroundColor
            )

            Spacer(modifier = Modifier.height(40.dp))

            // --- BUTTON ---
            Button(
                onClick = {
                    // Handle Generation Logic Here
                    navController.navigate("dashboard")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentBlue
                )
            ) {
                Text(
                    text = "Generate My Plan",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// --- HELPER COMPOSABLES ---

@Composable
fun ConfigLabel(text: String) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = 14.sp,
            color = Color(0xFF94A3B8), // Slate-400
            fontWeight = FontWeight.Medium
        ),
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun ConfigTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    backgroundColor: Color
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = Color.Gray) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = backgroundColor,
            unfocusedContainerColor = backgroundColor,
            focusedBorderColor = Color(0xFF3B82F6),
            unfocusedBorderColor = Color(0xFF334155), // Dark slate border
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            cursorColor = Color.White
        )
    )
}

@Composable
fun ConfigDropdown(
    options: List<String>,
    selected: String,
    onSelect: (String) -> Unit,
    backgroundColor: Color
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(backgroundColor, RoundedCornerShape(12.dp))
            .border(1.dp, Color(0xFF334155), RoundedCornerShape(12.dp))
            .clickable { expanded = true }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selected,
                color = Color.White,
                fontSize = 16.sp
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Dropdown",
                tint = Color.Gray
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color(0xFF1E293B))
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option, color = Color.White) },
                    onClick = {
                        onSelect(option)
                        expanded = false
                    }
                )
            }
        }
    }
}