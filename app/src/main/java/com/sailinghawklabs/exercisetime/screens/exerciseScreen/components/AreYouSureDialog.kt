package com.sailinghawklabs.exercisetime.screens.exerciseScreen.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun AreYouSureDialog(
    title: String,
    detail: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    confirmLabel: String = "Yes",
    dismissLabel: String = "Cancel",
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = title)
        },
        text = {
            Text(text = detail)
        },

        confirmButton = {
            Button(onClick = onConfirm) {
                Text(text = confirmLabel)
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = dismissLabel)
            }
        }
    )
}

