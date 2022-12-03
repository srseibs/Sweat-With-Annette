package com.sailinghawklabs.exercisetime.screens.finishedScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sailinghawklabs.exercisetime.R
import com.sailinghawklabs.exercisetime.ui.theme.ExerciseTimeTheme

@Composable
fun FinishedScreen(
    modifier: Modifier = Modifier,
    goBack: () -> Unit,
) {
    FinishedScreenContent(
        modifier = modifier,
        goBack = goBack,
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinishedScreenContent(
    modifier: Modifier = Modifier,
    goBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                title = {
                    Text(
                        text = "Sweat with Annette",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back arrow"
                        )
                    }
                }
            )
        },

        ) { scaffoldPadding ->

        Column(
            modifier = modifier
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(scaffoldPadding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                painter = painterResource(R.drawable.ic_finished_logo),
                contentDescription = "Finished picture"
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "You are done with your workout!",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
            )
            Button(
                modifier = Modifier.fillMaxWidth(0.8f),
                onClick = goBack
            ) {
                Text(
                    text = "Finish",
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun FinishedScreenPreview() {
    ExerciseTimeTheme {
        FinishedScreenContent(
            goBack = {}
        )
    }
}
