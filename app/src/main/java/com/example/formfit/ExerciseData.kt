package com.example.formfit

data class Exercise(
    val name: String,
    val reps: String,
    val muscles: String,
    val gifUrl: String,
    val category: String,
    val steps: List<String>
)

val exerciseList = listOf(

    // 🔴 PUSH DAY
    Exercise(
        name = "Bench Press",
        reps = "4 x 8",
        muscles = "Chest, Triceps",
        gifUrl = "",
        category = "Push",
        steps = listOf("Lie on bench", "Lower bar", "Press upward")
    ),

    Exercise(
        name = "Incline Dumbbell Press",
        reps = "3 x 12",
        muscles = "Upper Chest",
        gifUrl = "",
        category = "Push",
        steps = listOf("Set incline", "Lower dumbbells", "Press up")
    ),

    Exercise(
        name = "Shoulder Press",
        reps = "4 x 10",
        muscles = "Deltoids",
        gifUrl = "",
        category = "Push",
        steps = listOf("Hold dumbbells", "Press up", "Lower slowly")
    ),

    Exercise(
        name = "Arnold Press",
        reps = "3 x 12",
        muscles = "Deltoids",
        gifUrl = "",
        category = "Push",
        steps = listOf("Rotate wrists while pressing", "Lower slowly")
    ),

    Exercise(
        name = "Cable Chest Fly",
        reps = "3 x 15",
        muscles = "Chest",
        gifUrl = "",
        category = "Push",
        steps = listOf("Bring handles together", "Control return")
    ),

    Exercise(
        name = "Overhead Tricep Extension",
        reps = "3 x 12",
        muscles = "Triceps",
        gifUrl = "",
        category = "Push",
        steps = listOf("Lower behind head", "Extend arms upward")
    ),

    // 🔵 PULL DAY
    Exercise(
        name = "Pull Ups",
        reps = "3 x 10",
        muscles = "Back, Biceps",
        gifUrl = "",
        category = "Pull",
        steps = listOf("Grip bar", "Pull up", "Lower slowly")
    ),

    Exercise(
        name = "Lat Pulldown",
        reps = "4 x 12",
        muscles = "Lats",
        gifUrl = "",
        category = "Pull",
        steps = listOf("Pull to chest", "Release slowly")
    ),

    Exercise(
        name = "Seated Cable Row",
        reps = "4 x 10",
        muscles = "Mid Back",
        gifUrl = "",
        category = "Pull",
        steps = listOf("Pull to waist", "Extend slowly")
    ),

    Exercise(
        name = "Barbell Row",
        reps = "4 x 8",
        muscles = "Back",
        gifUrl = "",
        category = "Pull",
        steps = listOf("Bend forward", "Pull bar to stomach")
    ),

    Exercise(
        name = "Preacher Curl",
        reps = "3 x 12",
        muscles = "Biceps",
        gifUrl = "",
        category = "Pull",
        steps = listOf("Curl upward", "Lower slowly")
    ),

    Exercise(
        name = "Cable Hammer Curl",
        reps = "3 x 15",
        muscles = "Biceps, Forearms",
        gifUrl = "",
        category = "Pull",
        steps = listOf("Curl with neutral grip", "Control on way down")
    ),

    // 🟢 LEGS
    Exercise(
        name = "Barbell Squat",
        reps = "4 x 8",
        muscles = "Quads, Glutes",
        gifUrl = "",
        category = "Legs",
        steps = listOf("Lower body", "Push up")
    ),

    Exercise(
        name = "Romanian Deadlift",
        reps = "4 x 10",
        muscles = "Hamstrings",
        gifUrl = "",
        category = "Legs",
        steps = listOf("Hinge at hips", "Return upright")
    ),

    Exercise(
        name = "Bulgarian Split Squat",
        reps = "3 x 12 each leg",
        muscles = "Quads, Glutes",
        gifUrl = "",
        category = "Legs",
        steps = listOf("Place back foot on bench", "Lower front leg", "Push up")
    ),

    Exercise(
        name = "Walking Lunges",
        reps = "3 x 20 steps",
        muscles = "Quads, Glutes",
        gifUrl = "",
        category = "Legs",
        steps = listOf("Step forward", "Lower knee", "Push forward")
    ),

    Exercise(
        name = "Seated Calf Raise",
        reps = "4 x 15",
        muscles = "Calves",
        gifUrl = "",
        category = "Legs",
        steps = listOf("Raise heels upward", "Lower slowly")
    ),

    // 🟣 CORE
    Exercise(
        name = "Plank",
        reps = "3 x 60 sec",
        muscles = "Core",
        gifUrl = "",
        category = "Core",
        steps = listOf("Keep body straight", "Engage core", "Hold position")
    ),

    Exercise(
        name = "Hanging Leg Raise",
        reps = "3 x 12",
        muscles = "Lower Abs",
        gifUrl = "",
        category = "Core",
        steps = listOf("Raise legs upward", "Lower slowly")
    ),

    Exercise(
        name = "Russian Twist",
        reps = "3 x 20",
        muscles = "Obliques",
        gifUrl = "",
        category = "Core",
        steps = listOf("Twist torso side to side")
    )
)