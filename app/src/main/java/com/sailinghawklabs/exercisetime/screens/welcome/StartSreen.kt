package com.sailinghawklabs.exercisetime.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.sailinghawklabs.exercisetime.R
import com.sailinghawklabs.exercisetime.components.CircleButtton
import com.sailinghawklabs.exercisetime.ui.theme.ExerciseTimeTheme

@Composable
fun StartSreen(
    modifier: Modifier = Modifier
) {
    StartScreenContent()
}

@Composable
fun StartScreenContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ){
        Image(
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            painter = painterResource(id = R.drawable.start_logo),
            contentDescription = "app logo")

        CircleButtton(
            modifier = Modifier.fillMaxWidth(0.5f),
            buttonText = "Start",
            onClicked = { /*TODO*/ }
        )
    }
}


@Preview
@Composable
fun StartSreenPreview() {
    ExerciseTimeTheme {
        StartScreenContent()
    }
}