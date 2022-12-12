package com.sailinghawklabs.sweatwithannette

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.navigation.compose.rememberNavController
import com.sailinghawklabs.sweatwithannette.navigation.Navigation
import com.sailinghawklabs.sweatwithannette.ui.theme.SweatAnnetteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SweatAnnetteTheme {
                Navigation(navController = rememberNavController())
            }
        }
    }
}


