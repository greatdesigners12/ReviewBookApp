package com.example.reviewbookapp.screens.BookList

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.reviewbookapp.DataWrapper.FirebaseDataQueryWrapper
import com.example.reviewbookapp.components.SimpleAppBar
import com.example.reviewbookapp.components.boldText
import com.example.reviewbookapp.components.inputField
import com.example.reviewbookapp.model.Book
import com.example.reviewbookapp.model.BookInfo
import com.example.reviewbookapp.model.Item
import com.example.reviewbookapp.navigation.NavigationEnum
import kotlinx.coroutines.flow.asStateFlow
import java.lang.Exception


@ExperimentalComposeUiApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AppBookListScreen(navController : NavController = NavController(LocalContext.current), viewModel: BookListViewModel) {
    val searchValue = remember {
        mutableStateOf("")
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    Scaffold(topBar = { SimpleAppBar(title = "Search", navController = navController) }) {
        Box(contentAlignment = Alignment.TopCenter, modifier = Modifier.padding(5.dp)) {
            Column {
                inputField(
                    valueState = searchValue,
                    labelId = "Search",
                    enabled = true,
                    onValueChange = {
                        searchValue.value = it
                    },
                    onAction = KeyboardActions {
                        keyboardController?.hide()
                        viewModel.getAllBook(searchValue.value)
                    })
                if (viewModel.bookList.collectAsState().value.loading == true) {
                    CircularProgressIndicator()
                } else {
                    LazyColumn {

                        viewModel.bookList.value.data?.let { it1 ->
                            items(items = it1.items) { book ->
                                bookSearchListCard(book){
                                    navController.navigate(NavigationEnum.AppDetailsScreen.name + "/" + book.id)
                                }
                            }
                        }


                    }

                }
            }

        }
    }
}

@Composable
fun bookSearchListCard(book : Item, onClick : () -> Unit){

    Card(modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth().clickable {
            onClick()
        }, elevation = 5.dp){
        Row(modifier = Modifier.padding(5.dp), verticalAlignment = Alignment.CenterVertically){
            AsyncImage(model = book.volumeInfo.imageLinks.smallThumbnail, contentDescription = "")
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.fillMaxWidth()){
                boldText(text = book.volumeInfo.title, size = 15) }

            }
        }
    }
