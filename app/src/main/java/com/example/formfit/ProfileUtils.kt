package com.example.formfit

class ProfileUtils {fun getAgeCategory(age: Int): String {
    return when {
        age < 18 -> "Teen"
        age < 40 -> "Adult"
        age < 60 -> "Middle Age"
        else -> "Senior"
    }
}
}