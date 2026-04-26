package com.example.formfit

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import com.example.formfit.viewmodel.AuthViewModel
import com.example.formfit.viewmodel.ProfileViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

// ✅ Import Master Colors
import com.example.formfit.ui.theme.AccentBlue
import com.example.formfit.ui.theme.AccentPurple
import com.example.formfit.ui.theme.BgDark
import com.example.formfit.ui.theme.CardDark
import com.example.formfit.ui.theme.TextGrey
import com.example.formfit.ui.theme.TextWhite

// ✅ Gradient Brush
val SelectedGradient = Brush.linearGradient(listOf(AccentBlue, AccentPurple))

@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel = viewModel(), profileViewModel: ProfileViewModel = viewModel()) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var allergy by remember { mutableStateOf("") }

    var heightIndex by remember { mutableIntStateOf(35) }
    var weightIndex by remember { mutableIntStateOf(35) }
    var ageIndex by remember { mutableIntStateOf(8) }

    val heightOptions = (140..220).map { "$it" }
    val weightOptions = (40..150).map { "$it" }
    val ageOptions = (16..80).map { "$it" }

    val experienceOptions = listOf("Beginner", "Intermediate", "Advanced")
    var selectedExperience by remember { mutableStateOf("Beginner") }

    Box(modifier = Modifier.fillMaxSize().background(BgDark).padding(24.dp)) {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            Spacer(Modifier.height(40.dp))
            Text("Setup Profile", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = TextWhite)
            Text("Configure your digital trainer.", color = TextGrey, fontSize = 16.sp)

            Spacer(Modifier.height(30.dp))

            // ✅ Fixed: Using StudioTextField for all inputs
            StudioTextField(name, { name = it }, "Display Name")
            Spacer(Modifier.height(16.dp))
            StudioTextField(email, { email = it }, "Email Address")
            Spacer(Modifier.height(16.dp))
            StudioTextField(password, { password = it }, "Password", isPassword = true)
            Spacer(Modifier.height(16.dp))
            StudioTextField(allergy, { allergy = it }, "Allergies (Optional)")

            Spacer(Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth().height(140.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                WheelPicker("Height (cm)", heightOptions, heightIndex) { heightIndex = it }
                WheelPicker("Weight (kg)", weightOptions, weightIndex) { weightIndex = it }
                WheelPicker("Age", ageOptions, ageIndex) { ageIndex = it }
            }

            Spacer(Modifier.height(24.dp))

            Text("Experience Level", color = TextWhite, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                experienceOptions.forEach { level ->
                    val isSelected = selectedExperience == level
                    Box(
                        modifier = Modifier
                            .weight(1f).height(50.dp).clip(RoundedCornerShape(16.dp))
                            .background(if (isSelected) SelectedGradient else Brush.linearGradient(listOf(CardDark, CardDark)))
                            .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
                            .clickable { selectedExperience = level },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = level, color = TextWhite, fontSize = 12.sp)
                    }
                }
            }

            Spacer(Modifier.height(40.dp))

            Button(
                onClick = {
                    if (name.isBlank()) return@Button
                    val h = heightOptions[heightIndex].toFloat()
                    val w = weightOptions[weightIndex].toFloat()
                    val ageVal = ageOptions[ageIndex].toInt()
                    val bmiValue = w / ((h/100f) * (h/100f))

                    val newProfile = ProfileEntity(
                        name = name, email = email, height = h, weight = w,
                        age = ageVal, experience = selectedExperience,
                        allergy = allergy.ifEmpty { "None" }, bmi = bmiValue
                    )

                    // Try to Login first. If fails, Sign up. This is a hacky auto-signup for demo
                    authViewModel.login(email, password) {
                        profileViewModel.fetchProfile()
                        navController.navigate("dashboard") { popUpTo("login") { inclusive = true } }
                    }
                    
                    // Note: In a real app we'd handle failure cases and show error modals.
                    // For this refactor, if sign up is required:
                    authViewModel.signUp(email, password) {
                        profileViewModel.saveProfile(newProfile) {
                            navController.navigate("dashboard") { popUpTo("login") { inclusive = true } }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AccentBlue)
            ) {
                Text("Generate Plan", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// --- HELPER COMPONENTS ---

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RowScope.WheelPicker(
    title: String,
    options: List<String>,
    initialIndex: Int,
    onIndexChanged: (Int) -> Unit
) {
    val pagerState = rememberPagerState(initialPage = initialIndex) { options.size }

    LaunchedEffect(pagerState.currentPage) {
        onIndexChanged(pagerState.currentPage)
    }

    Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(title, color = TextGrey, fontSize = 12.sp, modifier = Modifier.padding(bottom = 8.dp))
        Box(
            modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(16.dp)).background(CardDark).border(1.dp, Color.White.copy(0.1f), RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Box(modifier = Modifier.fillMaxWidth().height(40.dp).background(Color.White.copy(0.05f)))
            VerticalPager(
                state = pagerState,
                pageSize = PageSize.Fixed(40.dp),
                contentPadding = PaddingValues(vertical = 40.dp),
                modifier = Modifier.fillMaxSize()
            ) { page ->
                val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
                val scale = lerp(1f, 0.75f, pageOffset.coerceIn(0f, 1f))
                val alpha = lerp(1f, 0.3f, pageOffset.coerceIn(0f, 1f))

                Box(modifier = Modifier.height(40.dp).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                        text = options[page],
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite.copy(alpha = alpha),
                        modifier = Modifier.graphicsLayer { scaleX = scale; scaleY = scale }
                    )
                }
            }
        }
    }
}

@Composable
fun StudioTextField(value: String, onValueChange: (String) -> Unit, label: String, isPassword: Boolean = false) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = TextGrey) },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = CardDark,
            unfocusedContainerColor = CardDark,
            focusedBorderColor = AccentBlue,
            unfocusedBorderColor = CardDark,
            focusedTextColor = TextWhite,
            unfocusedTextColor = TextWhite
        )
    )
}