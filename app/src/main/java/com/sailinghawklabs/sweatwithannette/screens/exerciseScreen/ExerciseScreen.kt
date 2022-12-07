package com.sailinghawklabs.sweatwithannette.screens.exerciseScreen

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sailinghawklabs.sweatwithannette.R
import com.sailinghawklabs.sweatwithannette.screens.exerciseScreen.components.AreYouSureDialog
import com.sailinghawklabs.sweatwithannette.screens.exerciseScreen.components.CircularProgress
import com.sailinghawklabs.sweatwithannette.screens.exerciseScreen.components.NumberedProgressIndicator
import com.sailinghawklabs.sweatwithannette.screens.historyScreen.HistoryViewModel
import com.sailinghawklabs.sweatwithannette.ui.theme.ExerciseTimeTheme
import com.sailinghawklabs.sweatwithannette.util.DefaultExerciseList
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExerciseScreen(
    modifier: Modifier = Modifier,
    goBack: () -> Unit,
    allDone: () -> Unit,
    viewModel: ExerciseViewModel = hiltViewModel(),
    finishedViewModel: HistoryViewModel = hiltViewModel(),
) {

    var showAlertDialog by remember { mutableStateOf(false) }

    BackHandler {
        showAlertDialog = true
    }

    if (showAlertDialog) {
        viewModel.pauseTimer()
        AreYouSureDialog(
            title = "Quit Exercises?",
            detail = "Are you sure you want to abandon your exercise?",
            onConfirm = {
                showAlertDialog = false
                finishedViewModel.addWorkoutToDatabase(false)
                goBack()
            },
            onDismiss = {
                showAlertDialog = false
                viewModel.resumeTimer()
            }
        )
    }

    val elapsedTime = viewModel.elapsedTime
    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel.spokenPrompt) {
        viewModel.textToSpeech(context, viewModel.spokenPrompt)
    }

    LaunchedEffect(key1 = viewModel.playSound) {
        viewModel.playSound?.let {
            viewModel.playSound(
                context.resources.openRawResourceFd(it),
            )
        }
    }

    LaunchedEffect(key1 = viewModel.allDoneWithExercises) {
        if (viewModel.allDoneWithExercises) {
            allDone()
        }
    }

    ExerciseScreenContent(
        modifier = modifier,
        goBack = {
            showAlertDialog = true
        },
        elapsedTime = elapsedTime.value,
        imageId = viewModel.exerciseImageId,
        textPrompt = viewModel.textPrompt,
        textValue = viewModel.textValue,
        timeDuration = viewModel.timeDuration.value,
        numExercises = viewModel.exerciseList.size,
        exercisesComplete = viewModel.exercisesComplete
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseScreenContent(
    modifier: Modifier = Modifier,
    goBack: () -> Unit,
    elapsedTime: Duration,
    timeDuration: Duration,
    @DrawableRes imageId: Int?,
    textPrompt: String,
    textValue: String,
    numExercises: Int,
    exercisesComplete: Int,
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
                .padding(scaffoldPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                var useLogoImage by remember { mutableStateOf(false) }
                useLogoImage = (imageId == null)


                if (imageId != null) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentScale = ContentScale.FillWidth,
                        painter = painterResource(imageId),
                        contentDescription = "Exercise pic"
                    )
                }

                AnimatedVisibility(
                    visible = useLogoImage,
                    enter = fadeIn(animationSpec = tween(800)),
//                    exit = fadeOut()
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth(.85f)
                            .weight(1f),
                        contentScale = ContentScale.FillWidth,
                        painter = painterResource(R.drawable.annette_headonly),
                        contentDescription = "Logo Head"
                    )
                }

                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = textPrompt,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.headlineSmall,
                )
                Text(
                    text = textValue,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.size(8.dp))
                CircularProgress(
                    textStyle = MaterialTheme.typography.displaySmall,
                    modifier = Modifier.fillMaxWidth(0.20f),
                    remainingTimeInMillis = timeDuration.inWholeMilliseconds - elapsedTime.inWholeMilliseconds,
                    maxTimeTimeInMillis = timeDuration.inWholeMilliseconds,
                )
                Spacer(modifier = Modifier.size(16.dp))
                NumberedProgressIndicator(
                    numExercises = numExercises,
                    activeExerciseIndex = exercisesComplete,
                )
                Spacer(modifier = Modifier.size(16.dp))
            }

        }
    }
}


@Preview
@Composable
fun ExerciseScreenPreview() {
    ExerciseTimeTheme {
        ExerciseScreenContent(
            goBack = {},
            textPrompt = "Text Prompt",
            textValue = "Text Value",
            elapsedTime = 10.seconds,
            timeDuration = 15.seconds,
            imageId = R.drawable.ic_side_plank,
            numExercises = DefaultExerciseList.size,
            exercisesComplete = 2,
        )
    }
}