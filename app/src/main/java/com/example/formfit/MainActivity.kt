package com.example.formfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController // Make sure this is imported

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
        // 1. CHANGE THIS LINE (LoginScreen -> FormFitConfigScreen)
        composable("login") {
            FormFitConfigScreen(navController)
        }

        composable("dashboard") {
            DashboardScreen(navController)
        }

        // NOTE: Ensure DietScreen and ExerciseDetailScreen are created in your project
        // or these lines will also throw errors.
        composable("diet") {
            // DietScreen()
        }

        composable("exercise/{exerciseName}") { backStackEntry ->
            val exerciseName = backStackEntry.arguments?.getString("exerciseName")

            // Ensure 'exerciseList' is defined somewhere in your project
            // val exercise = exerciseList.find { it.name == exerciseName }

            // exercise?.let { ExerciseDetailScreen(it) }
        }
    }
}