package com.example.formfit

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import kotlinx.coroutines.delay
import android.media.MediaPlayer
import android.provider.Settings
import androidx.compose.ui.platform.LocalContext


@Composable
fun ExerciseDetailScreen(exercise: Exercise) {

    var setsCompleted by remember { mutableStateOf(0) }
    val totalSets = 4

    var selectedRestTime by remember { mutableStateOf(60) }
    var timeLeft by remember { mutableStateOf(60) }
    var isRunning by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val mediaPlayer = remember {
        MediaPlayer.create(
            context,
            Settings.System.DEFAULT_NOTIFICATION_URI
        )
    }
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }



    // Timer Logic
    LaunchedEffect(isRunning, timeLeft) {

        if (isRunning && timeLeft > 0) {
            delay(1000L)
            timeLeft--
        }

        if (timeLeft == 0 && isRunning) {
            isRunning = false
            mediaPlayer.start()   // 🔔 PLAY SOUND
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B1423))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Text(
            exercise.name,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 🔥 SET COUNTER
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1B263B)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(16.dp)) {

                Text(
                    "Sets Completed",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Button(
                        onClick = {
                            if (setsCompleted > 0) setsCompleted--
                        }
                    ) { Text("-") }

                    Text(
                        "$setsCompleted / $totalSets",
                        color = Color.White,
                        fontSize = 20.sp
                    )

                    Button(
                        onClick = {
                            if (setsCompleted < totalSets) setsCompleted++
                        }
                    ) { Text("+") }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 🔥 REST TIMER CARD
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1B263B)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(16.dp)) {

                Text(
                    "Rest Timer",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                // ✅ REST TIME SELECTOR
                Text("Select Rest Time", color = Color.White)

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    listOf(30, 60, 90).forEach { time ->
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                selectedRestTime = time
                                timeLeft = time
                                isRunning = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor =
                                    if (selectedRestTime == time)
                                        Color(0xFF2A7BFF)
                                    else
                                        Color.DarkGray
                            )
                        ) {
                            Text("$time s")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "$timeLeft sec",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row {

                    Button(
                        onClick = { isRunning = !isRunning }
                    ) {
                        Text(if (isRunning) "Pause" else "Start")
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Button(
                        onClick = {
                            isRunning = false
                            timeLeft = selectedRestTime
                        }
                    ) {
                        Text("Reset")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 🔥 EXERCISE STEPS
        Text(
            "How To Perform:",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        exercise.steps.forEachIndexed { index, step ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF1B263B)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {
                Text(
                    "${index + 1}. $step",
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

