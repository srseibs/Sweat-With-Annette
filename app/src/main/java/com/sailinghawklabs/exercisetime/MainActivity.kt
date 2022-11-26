package com.sailinghawklabs.exercisetime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sailinghawklabs.exercisetime.screens.welcome.StartScreen
import com.sailinghawklabs.exercisetime.ui.theme.ExerciseTimeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExerciseTimeTheme {

                StartScreen()
            }
        }
    }
}


