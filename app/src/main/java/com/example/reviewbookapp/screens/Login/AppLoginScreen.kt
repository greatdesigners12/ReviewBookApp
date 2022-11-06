package com.example.reviewbookapp.screens.Login


import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.reviewbookapp.DataWrapper.FirebaseDataWrapper.Status.FAILED
import com.example.reviewbookapp.DataWrapper.FirebaseDataWrapper.Status.SUCCESS
import com.example.reviewbookapp.components.Logo
import com.example.reviewbookapp.components.authForm
import com.example.reviewbookapp.navigation.NavigationEnum
import com.example.reviewbookapp.utils.simpleAlertDialog

@ExperimentalComposeUiApi
@Composable
fun AppLoginScreen(loginScreenViewModel: LoginScreenViewModel ,navController: NavController) {
    val isLoading = remember {
        mutableStateOf(false)
    }
    val showAlertDialog = remember{
        mutableStateOf(false)
    }

    val isRegister = remember {
        mutableStateOf(false)
    }

    val networkMsg = remember{
        mutableStateOf(loginScreenViewModel.msg.value)
    }

    LaunchedEffect(key1 = loginScreenViewModel.msg.collectAsState().value.message){
        networkMsg.value = loginScreenViewModel.msg.value
        if(networkMsg.value.status == FAILED){
            showAlertDialog.value = true

        }

        if(networkMsg.value.status == SUCCESS){
            if(networkMsg.value.message.equals("LOGIN")){
                navController.popBackStack()
                navController.navigate(NavigationEnum.AppMainScreen.name)
            }else{
                isRegister.value = false

            }
        }

        isLoading.value = false
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp), contentAlignment = Alignment.TopCenter){
        if (showAlertDialog.value){
            simpleAlertDialog(if(networkMsg.value.status == SUCCESS) "SUCCESS" else "FAILED",networkMsg.value.message){
                showAlertDialog.value = false
                isLoading.value = false
            }
        }else{
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Logo(fontSize = 30.sp)
                authForm(isLoading.value, {isRegister.value = !isRegister.value},isRegister.value){em, pwd ->
                    isLoading.value = true
                    if(isRegister.value){
                        loginScreenViewModel.register(email = em, password = pwd)
                    }else{
                        loginScreenViewModel.signIn(email = em, password = pwd)
                    }


                }
            }

        }
    }
}

