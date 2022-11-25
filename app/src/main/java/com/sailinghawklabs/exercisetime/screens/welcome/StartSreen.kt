package com.sailinghawklabs.exercisetime.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.sailinghawklabs.exercisetime.R
import com.sailinghawklabs.exercisetime.ui.theme.ExerciseTimeTheme

@Composable
fun StartSreen(
    modifier: Modifier = Modifier
) {
    
}

@Composable
fun StartScreenContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ){
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.start_logo),
            contentDescription = "app logo")
    }
}


@Preview
@Composable
fun StartSreenPreview() {
    ExerciseTimeTheme {
        StartScreenContent()
    }
}