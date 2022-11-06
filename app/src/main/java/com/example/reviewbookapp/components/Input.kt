package com.example.reviewbookapp.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp


@Composable
fun emailInput(modifier: Modifier = Modifier, emailState : MutableState<String>, labelId : String = "Email", enabled : Boolean = true,
                imeAction: ImeAction = ImeAction.Next, onAction: KeyboardActions = KeyboardActions.Default){
    inputField(modifier, valueState = emailState, labelId, enabled, imeAction=imeAction, onAction = onAction)
}

@Composable
fun passwordInput(modifier: Modifier = Modifier, passwordState : MutableState<String>, labelId : String = "Password", enabled : Boolean = true,
               imeAction: ImeAction = ImeAction.Next, onAction: KeyboardActions = KeyboardActions.Default, visibility : MutableState<Boolean>){
    val visualTransformation = if(visibility.value) VisualTransformation.None else PasswordVisualTransformation()
    OutlinedTextField(value = passwordState.value, onValueChange = {passwordState.value = it}, label = {Text(labelId)}, modifier = modifier.padding(10.dp),
        enabled = true, keyboardActions = onAction, keyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = KeyboardType.Password),
        visualTransformation =visualTransformation, trailingIcon = { IconButton(onClick = { visibility.value = !visibility.value }){
            Icon(imageVector= if(visibility.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff, "")}
        }
    )
}

@Composable
fun inputField(
    modifier: Modifier = Modifier, valueState: MutableState<String>, labelId: String, enabled: Boolean, isSingleLine : Boolean = true, onValueChange : (String) -> Unit = {valueState.value = it},
    keyboardType: KeyboardType = KeyboardType.Text, imeAction: ImeAction = ImeAction.Next, onAction: KeyboardActions = KeyboardActions.Default
){

    OutlinedTextField(value = valueState.value, onValueChange = {onValueChange(it)}, label = {Text(labelId)}, modifier = modifier.padding(10.dp),
    enabled = true, keyboardActions = onAction, keyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = keyboardType),

    )
}