package com.example.formfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
// import androidx.navigation.compose.rememberNavController // Not needed if using the one from navigation-compose

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FormFitApp()
        }
    }
}

@Composable
fun FormFitApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") { LoginScreen(navController) }
        composable("dashboard") { DashboardScreen(navController) }
        composable("diet") { DietScreen() }
        composable("ai_plan") { AiPlanScreen() }
        composable("profile") { ProfileScreen(navController) }
        composable("exercise/{exerciseName}") { backStackEntry ->
            val exerciseName = backStackEntry.arguments?.getString("exerciseName")
            val exercise = exerciseList.find { it.name == exerciseName }

            exercise?.let {
                ExerciseDetailScreen(it)
            }
        }
    } // ✅ Corrected: Only one closing brace here for NavHost
    }
