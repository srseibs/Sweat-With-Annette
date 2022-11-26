package com.sailinghawklabs.exercisetime.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sailinghawklabs.exercisetime.screens.ExerciseScreen
import com.sailinghawklabs.exercisetime.screens.welcome.StartScreen


@Composable
fun Navigation(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.StartScreen.route
    ) {
        composable(
            route = NavigationRoutes.StartScreen.route
        ) {
            StartScreen(onStart = {
                navController.navigate(NavigationRoutes.ExerciseScreen.route) {
                    popUpTo(NavigationRoutes.StartScreen.route) {
                        inclusive = true
                    }
                }
            }
            )
        }

        composable(
            route = NavigationRoutes.ExerciseScreen.route
        ) {
            ExerciseScreen()
        }

    }
}