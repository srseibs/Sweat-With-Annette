package com.sailinghawklabs.exercisetime.navigation

sealed class NavigationRoutes(val route: String) {
    object StartScreen: NavigationRoutes("start_screen")
    object ExerciseScreen: NavigationRoutes("exercise_screen")
}

