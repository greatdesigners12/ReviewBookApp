package com.example.reviewbookapp.screens.UpdateBook

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.reviewbookapp.model.Book

@Composable
fun UpdateBookScreen(navController: NavController, viewModel: UpdateBookScreenViewModel, docId : String) {

    val title = remember{
        mutableStateOf("")
    }

    val description = remember{
        mutableStateOf("")
    }

    val isReading = remember{
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = viewModel.data.collectAsState().value.data){

        viewModel.getDetailBook(docId)
        title.value = viewModel.data.value.data?.title.toString()
        description.value = viewModel.data.value.data?.description.toString()
        isReading.value = viewModel.data.value.data?.reading == true
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp), contentAlignment = Alignment.TopCenter) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if(!isReading.value){
                Button(onClick = {
                    viewModel.updateBook(Book(id = docId, title = title.value, description = description.value, reading = true), navController)
                }){
                    Text( "Read this book" )
                }
            }

            TextField(value = title.value, onValueChange = {
                title.value = it
            }, label = {Text("Title")})
            TextField(value = description.value, onValueChange = {
                description.value = it
            }, label = {Text("Description".replace("/<\\/?[^>]+(>|\$)/g", ""))}, modifier = Modifier.height(200.dp))
            Button(onClick = {
                viewModel.updateBook(Book(id = docId, title = title.value, description = description.value), navController)
            }){
                Text("Update")
            }
        }
    }
}