package com.sailinghawklabs.exercisetime.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sailinghawklabs.exercisetime.screens.exerciseScreen.ExerciseScreen
import com.sailinghawklabs.exercisetime.screens.finishedScreen.FinishedScreen
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
            StartScreen(
                onStart = {
                    navController.navigate(NavigationRoutes.ExerciseScreen.route)
                }
            )
        }

        composable(
            route = NavigationRoutes.ExerciseScreen.route
        ) {

            ExerciseScreen(
                goBack = {
                    navController.popBackStack()
                },
                allDone = {
                    navController.navigate(NavigationRoutes.FinishedScreen.route)
                }
            )
        }

        composable(
            route = NavigationRoutes.FinishedScreen.route
        ) {
            FinishedScreen(
                goBack = {
                    navController.popBackStack(NavigationRoutes.StartScreen.route, inclusive = true)
                    navController.navigate(NavigationRoutes.StartScreen.route)

                }
            )

        }

    }
}
