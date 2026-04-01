package com.example.formfit

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes

data class Exercise(
    val name: String,
    val reps: String,
    val muscles: String,
    @DrawableRes val imageRes: Int,
    val category: String,
    val steps: List<String>,
    @RawRes val videoRes: Int? = null
)

val exerciseList = listOf(

    // 🔴 PUSH DAY (10 Exercises)
    // ✅ FIX: videoRes is now INSIDE the parenthesis!
    Exercise("Bench Press", "4 x 8", "Chest, Triceps", R.drawable.bench_press, "Push", listOf("Lie on bench", "Lower bar", "Press upward"), videoRes = R.raw.bench_press_video),
    Exercise("Incline Dumbbell Press", "3 x 12", "Upper Chest", R.drawable.incline_press, "Push", listOf("Set incline", "Lower dumbbells", "Press up")),
    Exercise("Shoulder Press", "4 x 10", "Deltoids", R.drawable.shoulder_press, "Push", listOf("Hold dumbbells", "Press up", "Lower slowly")),
    Exercise("Arnold Press", "3 x 12", "Deltoids", R.drawable.arnold_press, "Push", listOf("Rotate wrists while pressing", "Lower slowly")),
    Exercise("Cable Chest Fly", "3 x 15", "Chest", R.drawable.cable_fly, "Push", listOf("Bring handles together", "Control return")),
    Exercise("Overhead Tricep Extension", "3 x 12", "Triceps", R.drawable.tricep_ext, "Push", listOf("Lower behind head", "Extend arms upward")),
    // NEW PUSH EXERCISES
    Exercise("Dips", "3 x Max", "Lower Chest, Triceps", R.drawable.dips, "Push", listOf("Grip parallel bars", "Lower body until shoulders are below elbows", "Push back up")),
    Exercise("Lateral Raises", "4 x 15", "Side Deltoids", R.drawable.lateral_raises, "Push", listOf("Hold dumbbells at sides", "Raise arms until parallel with floor", "Lower slowly")),
    Exercise("Tricep Pushdown", "4 x 12", "Triceps", R.drawable.tricep_pushdown, "Push", listOf("Grip cable attachment", "Push weight down until arms lock out", "Control the return")),
    Exercise("Pec Deck Machine", "3 x 15", "Inner Chest", R.drawable.pec_deck, "Push", listOf("Sit with back flat", "Squeeze handles together", "Release slowly")),

    // 🔵 PULL DAY (10 Exercises)
    Exercise("Pull Ups", "3 x 10", "Back, Biceps", R.drawable.pull_ups, "Pull", listOf("Grip bar", "Pull up", "Lower slowly")),
    Exercise("Lat Pulldown", "4 x 12", "Lats", R.drawable.lat_pulldown, "Pull", listOf("Pull to chest", "Release slowly")),
    Exercise("Seated Cable Row", "4 x 10", "Mid Back", R.drawable.seated_row, "Pull", listOf("Pull to waist", "Extend slowly")),
    Exercise("Barbell Row", "4 x 8", "Back", R.drawable.barbell_row, "Pull", listOf("Bend forward", "Pull bar to stomach")),
    Exercise("Preacher Curl", "3 x 12", "Biceps", R.drawable.preacher_curl, "Pull", listOf("Curl upward", "Lower slowly")),
    Exercise("Cable Hammer Curl", "3 x 15", "Biceps, Forearms", R.drawable.hammer_curl, "Pull", listOf("Curl with neutral grip", "Control on way down")),
    // NEW PULL EXERCISES
    Exercise("Face Pulls", "4 x 15", "Rear Deltoids, Traps", R.drawable.face_pulls, "Pull", listOf("Set cable to upper chest height", "Pull rope towards face", "Squeeze shoulder blades")),
    Exercise("T-Bar Row", "4 x 10", "Mid Back, Lats", R.drawable.t_bar_row, "Pull", listOf("Straddle the bar", "Hinge at hips", "Pull weight towards chest")),
    Exercise("Dumbbell Bicep Curl", "4 x 12", "Biceps", R.drawable.dumbbell_curl, "Pull", listOf("Hold dumbbells at sides", "Supinate wrists and curl upwards", "Lower with control")),
    Exercise("Dumbbell Shrugs", "4 x 15", "Traps", R.drawable.shrugs, "Pull", listOf("Hold heavy dumbbells", "Shrug shoulders straight up", "Hold for one second, then lower")),

    // 🟢 LEGS (9 Exercises)
    // ✅ FIX: videoRes is now INSIDE the parenthesis!
    Exercise("Barbell Squat", "4 x 8", "Quads, Glutes", R.drawable.squat, "Legs", listOf("Lower body", "Push up"), videoRes = R.raw.squat),
    Exercise("Romanian Deadlift", "4 x 10", "Hamstrings", R.drawable.romanian_deadlift, "Legs", listOf("Hinge at hips", "Return upright")),
    Exercise("Bulgarian Split Squat", "3 x 12 each leg", "Quads, Glutes", R.drawable.split_squat, "Legs", listOf("Place back foot on bench", "Lower front leg", "Push up")),
    Exercise("Walking Lunges", "3 x 20 steps", "Quads, Glutes", R.drawable.lunges, "Legs", listOf("Step forward", "Lower knee", "Push forward")),
    Exercise("Seated Calf Raise", "4 x 15", "Calves", R.drawable.calf_raise, "Legs", listOf("Raise heels upward", "Lower slowly")),
    // NEW LEG EXERCISES
    Exercise("Leg Press", "4 x 12", "Quads, Glutes", R.drawable.leg_press, "Legs", listOf("Place feet shoulder-width on platform", "Lower weight until knees are at 90 degrees", "Press back up without locking knees")),
    Exercise("Leg Extension", "4 x 15", "Quads", R.drawable.leg_extension, "Legs", listOf("Sit on machine", "Extend legs fully", "Lower weight slowly")),
    Exercise("Hamstring Curl", "4 x 15", "Hamstrings", R.drawable.hamstring_curl, "Legs", listOf("Lie face down on machine", "Curl weight towards glutes", "Release with control")),
    Exercise("Barbell Hip Thrust", "4 x 10", "Glutes", R.drawable.hip_thrust, "Legs", listOf("Rest upper back on bench", "Place barbell over hips", "Drive hips upward and squeeze glutes")),

    // 🟣 CORE (6 Exercises)
    Exercise("Plank", "3 x 60 sec", "Core", R.drawable.plank, "Core", listOf("Keep body straight", "Engage core", "Hold position")),
    Exercise("Hanging Leg Raise", "3 x 12", "Lower Abs", R.drawable.leg_raise, "Core", listOf("Raise legs upward", "Lower slowly")),
    Exercise("Russian Twist", "3 x 20", "Obliques", R.drawable.russian_twist, "Core", listOf("Twist torso side to side")),
    // NEW CORE EXERCISES
    Exercise("Cable Crunch", "3 x 15", "Upper Abs", R.drawable.cable_crunch, "Core", listOf("Kneel below high pulley", "Hold rope behind neck", "Crunch downwards using only your abs")),
    Exercise("Bicycle Crunches", "3 x 40", "Obliques, Abs", R.drawable.bicycle_crunches, "Core", listOf("Lie on back", "Bring opposite elbow to opposite knee", "Alternate sides continuously")),
    Exercise("Dead Bug", "3 x 12 per side", "Deep Core", R.drawable.dead_bug, "Core", listOf("Lie flat with arms and legs raised", "Lower opposite arm and leg to the floor", "Return to start and switch sides"))
)