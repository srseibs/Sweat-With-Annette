package com.sailinghawklabs.sweatwithannette.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sailinghawklabs.sweatwithannette.screens.bmiScreen.BmiScreen
import com.sailinghawklabs.sweatwithannette.screens.exerciseScreen.ExerciseScreen
import com.sailinghawklabs.sweatwithannette.screens.finishedScreen.FinishedScreen
import com.sailinghawklabs.sweatwithannette.screens.historyScreen.HistoryScreen
import com.sailinghawklabs.sweatwithannette.screens.welcomeScreen.StartScreen
import com.sailinghawklabs.sweatwithannette.screens.workoutEdit.WorkoutEditScreen
import com.sailinghawklabs.sweatwithannette.screens.workoutsScreen.WorkoutsScreen

@Composable
fun Navigation(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.WelcomeScreen.route
    ) {

        navController.addOnDestinationChangedListener{_,destination, _ ->
            Log.d("Navigation", "Navigation route: ${destination.route}")
        }


        composable(
            route = NavigationRoutes.WelcomeScreen.route
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
                },
                goToWorkoutSets = {
                    navController.navigate(NavigationRoutes.WorkoutSetsScreen.route)
                }
            )
        }

        composable(
            route = NavigationRoutes.ExerciseScreen.route
        ) {
            ExerciseScreen(
                goBack = {
                    navController.navigate(NavigationRoutes.WelcomeScreen.route){
                        popUpTo(NavigationRoutes.WelcomeScreen.route){inclusive = true}
                    }
                },
                allDone = {
                    navController.navigate(NavigationRoutes.FinishedScreen.route){
                        popUpTo(NavigationRoutes.FinishedScreen.route){inclusive = true}
                    }
                }
            )
        }

        composable(
            route = NavigationRoutes.FinishedScreen.route
        ) {
            FinishedScreen(
                goBack = {
                    navController.popBackStack(
                        NavigationRoutes.WelcomeScreen.route,
                        inclusive = true
                    )
                    navController.navigate(NavigationRoutes.WelcomeScreen.route)
                }
            )

        }

        composable(
            route = NavigationRoutes.BmiScreen.route
        ) {
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

        composable(
            route = NavigationRoutes.WorkoutSetsScreen.route
        ) {
            WorkoutsScreen(
                goBack = {
                    navController.popBackStack()
                },
                goToWorkOutEdit = {
                    val baseRoute = NavigationRoutes.WorkoutEditScreen.route
                    val arg = if (it == null) "" else "?workoutName=$it"
                    navController.navigate(
                        route = "$baseRoute$arg"
                    )
                }
            )
        }

        composable(
            route = "${NavigationRoutes.WorkoutEditScreen.route}?workoutName={workoutName}",
            arguments = listOf(
                navArgument("workoutName") { defaultValue = "" }
            )
        ) { backStackEntry ->
            WorkoutEditScreen(
                goBack = {
                    navController.navigate(NavigationRoutes.WorkoutSetsScreen.route){
                        popUpTo(NavigationRoutes.WorkoutSetsScreen.route){inclusive = true}
                    }                },
            )
        }
    }
}
