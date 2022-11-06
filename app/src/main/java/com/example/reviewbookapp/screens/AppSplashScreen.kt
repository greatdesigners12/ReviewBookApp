package com.example.reviewbookapp.screens

import android.media.tv.TvContract
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.reviewbookapp.components.Logo
import com.example.reviewbookapp.navigation.NavigationEnum
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import javax.inject.Inject


@Composable
fun AppSplashScreen(navController: NavController) {
    val scale = remember{
        Animatable(0f)
    }
    LaunchedEffect(true){
        scale.animateTo(targetValue = 1f, tween(durationMillis = 1000, easing = {
            OvershootInterpolator(8f).getInterpolation(it)
        }))
        delay(2000L)
        val auth = FirebaseAuth.getInstance()
        navController.popBackStack()

        if(auth.currentUser == null){
            navController.navigate(NavigationEnum.AppLoginScreen.name)
        }else{
            navController.popBackStack()
            navController.navigate(NavigationEnum.AppMainScreen.name)
        }

    }

        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize().scale(scale = scale.value)) {
            Card(modifier = Modifier.size(250.dp), shape = CircleShape, border = BorderStroke(3.dp, color = Color.Gray)){
                Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Logo(25.sp)
                    Text("Read. Change. Yourself ")
                }
            }

        }



}

