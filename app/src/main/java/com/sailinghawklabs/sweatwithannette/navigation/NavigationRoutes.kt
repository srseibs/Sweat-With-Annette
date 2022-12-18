package com.sailinghawklabs.sweatwithannette.navigation

sealed class NavigationRoutes(val route: String) {
    object WelcomeScreen: NavigationRoutes("welcome_screen" )
    object ExerciseScreen: NavigationRoutes("exercise_screen" )
    object FinishedScreen: NavigationRoutes("finished_screen" )
    object BmiScreen: NavigationRoutes("bmi_screen")
    object HistoryScreen: NavigationRoutes("history_screen")
    object WorkoutSetsScreen: NavigationRoutes("workout_sets_screen")
    object WorkoutEditScreen: NavigationRoutes("workout_edit_screen")
}

