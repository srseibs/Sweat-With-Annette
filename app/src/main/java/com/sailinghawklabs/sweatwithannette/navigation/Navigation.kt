package com.sailinghawklabs.sweatwithannette.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sailinghawklabs.sweatwithannette.screens.bmiScreen.BmiScreen
import com.sailinghawklabs.sweatwithannette.screens.exerciseScreen.ExerciseScreen
import com.sailinghawklabs.sweatwithannette.screens.finishedScreen.FinishedScreen
import com.sailinghawklabs.sweatwithannette.screens.historyScreen.HistoryScreen
import com.sailinghawklabs.sweatwithannette.screens.welcomeScreen.StartScreen


@RequiresApi(Build.VERSION_CODES.O)
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
