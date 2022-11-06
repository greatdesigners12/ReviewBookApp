package com.example.reviewbookapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.tooling.preview.Preview
import com.example.reviewbookapp.navigation.AppNavigation
import com.example.reviewbookapp.ui.theme.ReviewBookAppTheme
import dagger.hilt.android.AndroidEntryPoint
import io.sentry.ISpan
import io.sentry.Sentry
import io.sentry.Span


@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            MainApp()
        }

    }
}

@ExperimentalComposeUiApi
@Composable
fun MainApp() {
    ReviewBookAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            AppNavigation()
        }
    }
}
@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainApp()
}