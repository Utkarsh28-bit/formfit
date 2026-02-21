package com.example.formfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.*

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

        composable("login") {
            LoginScreen(navController)
        }

        composable("dashboard") {
            DashboardScreen(navController)
        }

        composable("diet") {
            DietScreen()
        }

        composable("exercise/{exerciseName}") { backStackEntry ->
            val exerciseName =
                backStackEntry.arguments?.getString("exerciseName")

            val exercise = exerciseList.find {
                it.name == exerciseName
            }

            exercise?.let {
                ExerciseDetailScreen(it)
            }
        }
    }
}
