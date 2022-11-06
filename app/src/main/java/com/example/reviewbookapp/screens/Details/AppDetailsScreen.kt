package com.example.reviewbookapp.screens.Details

import android.content.ContentValues.TAG
import android.text.Html
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.parseAsHtml
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.reviewbookapp.DataWrapper.FirebaseDataQueryWrapper
import com.example.reviewbookapp.model.Book
import com.example.reviewbookapp.model.Item
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception

@Composable
fun AppDetailsScreen(navController: NavController, bookId : String, viewModel: DetailsScreenViewModel) {
    val curData = remember{
        mutableStateOf(FirebaseDataQueryWrapper<Item, Boolean, Exception>())
    }

    val isSaved = remember{
        mutableStateOf(false)

    }

    LaunchedEffect(true){
        viewModel.resetData()
        viewModel.getDetailBook(bookId)
        isSaved.value = viewModel.checkIfSaved(bookId)
    }

    LaunchedEffect(viewModel.data.collectAsState().value){
        curData.value = viewModel.data.value
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(5.dp), contentAlignment = if(viewModel.data.collectAsState().value.loading == true) Alignment.Center else Alignment.TopCenter, ){
        if(curData.value.loading == true){
            if(curData.value.e == null){
                CircularProgressIndicator()
            }else{
                navController.popBackStack()
            }

        }else{
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(model = viewModel.data.collectAsState().value.data?.volumeInfo?.imageLinks?.smallThumbnail, contentDescription = "", modifier = Modifier
                    .height(170.dp)
                    .width(90.dp))
                Spacer(modifier = Modifier.height(10.dp))
                viewModel.data.collectAsState().value.data?.volumeInfo?.description?.let { Text(text = Html.fromHtml(Html.fromHtml(it).toString()).toString(), style = MaterialTheme.typography.caption, fontSize = 12.sp) }
                Spacer(modifier = Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.SpaceAround) {
                    Button(onClick = {
                        viewModel.saveToFirebase(Book(title = curData.value.data?.volumeInfo?.title,
                            notes = "", author = curData.value.data?.volumeInfo?.authors?.get(0),
                            imageUrl = curData.value.data?.volumeInfo?.imageLinks?.smallThumbnail,
                            userId = FirebaseAuth.getInstance().currentUser?.uid,
                            description = curData.value.data?.volumeInfo?.description,
                            bookId = curData.value.data?.id,
                            reading = false
                        ), navController, isSaved.value)
                    }) {
                        Text(if(isSaved.value) "Remove From Saved List" else "Save")
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(onClick = {
                        navController.popBackStack()

                    }) {
                        Text("Cancel")
                    }
                }
            }
        }


    }
}