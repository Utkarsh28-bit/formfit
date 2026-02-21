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
        name = "Lateral Raises",
        reps = "3 x 15",
        muscles = "Side Delts",
        gifUrl = "",
        category = "Push",
        steps = listOf("Raise arms", "Control movement")
    ),

    Exercise(
        name = "Tricep Pushdown",
        reps = "3 x 15",
        muscles = "Triceps",
        gifUrl = "",
        category = "Push",
        steps = listOf("Push cable down", "Control return")
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
        name = "Barbell Bicep Curl",
        reps = "3 x 12",
        muscles = "Biceps",
        gifUrl = "",
        category = "Pull",
        steps = listOf("Curl bar up", "Lower slowly")
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
    )
)
