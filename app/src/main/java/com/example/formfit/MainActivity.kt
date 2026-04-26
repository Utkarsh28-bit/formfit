package com.example.formfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
// import androidx.navigation.compose.rememberNavController // Not needed if using the one from navigation-compose

import com.example.formfit.ui.theme.FormfitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FormfitTheme {
                FormFitApp()
            }
        }
    }
}

import com.example.formfit.viewmodel.AuthViewModel
import com.example.formfit.viewmodel.ProfileViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun FormFitApp() {
    val navController = rememberNavController()
    
    val authViewModel: AuthViewModel = viewModel()
    val profileViewModel: ProfileViewModel = viewModel()

    val currentUser = authViewModel.currentUser
    val startDestination = if (currentUser != null) "dashboard" else "login"

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("login") { LoginScreen(navController, authViewModel, profileViewModel) }
        composable("dashboard") { DashboardScreen(navController, profileViewModel) }
        composable("diet") { DietScreen() }
        composable("stats") { StatsScreen(navController) }
        composable("ai_plan") { AiPlanScreen(profileViewModel) }
        composable("profile") { ProfileScreen(navController, profileViewModel) }
        composable("exercise/{exerciseName}") { backStackEntry ->
            val exerciseName = backStackEntry.arguments?.getString("exerciseName")
            val exercise = exerciseList.find { it.name == exerciseName }

            exercise?.let {
                ExerciseDetailScreen(it)
            }
        }
    } // ✅ Corrected: Only one closing brace here for NavHost
    }
