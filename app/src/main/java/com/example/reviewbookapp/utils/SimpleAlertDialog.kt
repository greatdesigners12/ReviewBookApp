package com.example.reviewbookapp.utils

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun simpleAlertDialog(title : String,message : String, onDismiss : () -> Unit) {
    AlertDialog(onDismissRequest = { onDismiss() }, title = {Text(title)}, text = {Text(message)}, buttons = {})
}