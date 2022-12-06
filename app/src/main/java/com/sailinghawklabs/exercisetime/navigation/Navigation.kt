package com.sailinghawklabs.exercisetime.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sailinghawklabs.exercisetime.screens.bmiScreen.BmiScreen
import com.sailinghawklabs.exercisetime.screens.exerciseScreen.ExerciseScreen
import com.sailinghawklabs.exercisetime.screens.finishedScreen.FinishedScreen
import com.sailinghawklabs.exercisetime.screens.historyScreen.HistoryScreen
import com.sailinghawklabs.exercisetime.screens.welcomeScreen.StartScreen


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
                },
                goToBmi = {
                    navController.navigate(NavigationRoutes.BmiScreen.route)
                },
                goToHistory = {
                    navController.navigate(NavigationRoutes.HistoryScreen.route)
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

        composable(
            route = NavigationRoutes.BmiScreen.route
        ){
            BmiScreen(
                goBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = NavigationRoutes.HistoryScreen.route
        ) {
            HistoryScreen(
                goBack = {
                    navController.popBackStack()
                }
            )
        }

    }
}
