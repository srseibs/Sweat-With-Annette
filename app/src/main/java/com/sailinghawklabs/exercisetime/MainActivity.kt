package com.sailinghawklabs.exercisetime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.sailinghawklabs.exercisetime.navigation.Navigation
import com.sailinghawklabs.exercisetime.ui.theme.ExerciseTimeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExerciseTimeTheme {
                Navigation(navController = rememberNavController())
            }
        }
    }
}


