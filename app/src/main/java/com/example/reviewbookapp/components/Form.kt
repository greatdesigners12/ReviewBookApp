package com.example.reviewbookapp.components

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp

@ExperimentalComposeUiApi
@Composable
fun authForm(isLoading : Boolean, switchFunc : () -> Unit,isRegister : Boolean, onDone : (String, String) -> Unit) {

    val email = rememberSaveable {
        mutableStateOf("")
    }
    val password = rememberSaveable {
        mutableStateOf("")
    }
    val passwordVisibillity = rememberSaveable {
        mutableStateOf(false)
    }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }

    val resetInput = {
        email.value = ""
        password.value = ""
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()

            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        emailInput(
            modifier = Modifier.fillMaxWidth(),
            emailState = email,
            enabled = valid,
            onAction = KeyboardActions { passwordFocusRequest.requestFocus() })
        passwordInput(
            modifier = Modifier.fillMaxWidth(),
            passwordState = password,
            visibility = passwordVisibillity,
            enabled = valid,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                keyboardController?.hide()
            })
        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            enabled = valid,
            onClick = {
                if (valid) {
                    onDone(email.value, password.value)
                    keyboardController?.hide()
                    resetInput()
                }
            }) {
            if(isLoading){

                CircularProgressIndicator(color = Color.White)
            }else{
                Text(if (isRegister) "REGISTER" else "LOGIN")
            }



        }
        Row() {
            Text(if (isRegister) "Already have an account ? " else "Want to create an account ? ")
            Text(
                if (isRegister) "Login here" else "Register here",
                color = MaterialTheme.colors.primary,
                modifier = Modifier.clickable {
                    switchFunc()
                    resetInput()
                })
        }
    }

}