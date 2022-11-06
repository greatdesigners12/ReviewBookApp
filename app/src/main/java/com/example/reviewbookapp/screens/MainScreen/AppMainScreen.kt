package com.example.reviewbookapp.screens.MainScreen

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.text.style.AlignmentSpan
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.reviewbookapp.components.boldText
import com.example.reviewbookapp.model.Book
import com.example.reviewbookapp.navigation.NavigationEnum
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.sentry.ISpan
import io.sentry.Sentry
import io.sentry.SpanStatus

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AppMainScreen(navController: NavController, viewModel: MainScreenViewModel) {



    Scaffold(topBar = {
        appBar{
            FirebaseAuth.getInstance().signOut().run{
                navController.popBackStack()
                navController.navigate(NavigationEnum.AppLoginScreen.name)
            }
        }
    }, floatingActionButton = {
        fabContent{
            navController.navigate(NavigationEnum.AppBookListScreen.name)
        }
    }, modifier = Modifier
        .fillMaxSize()
        ){
        Column(modifier = Modifier
            .padding(5.dp)
            .verticalScroll(rememberScrollState())){
            if(viewModel.data.collectAsState().value.loading == true){
                CircularProgressIndicator()
            }else{
                Row( horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()){
                    boldText(text = "Welcome, bro", size = 25)
                    IconButton(onClick = { navController.navigate(NavigationEnum.AppUserStatsScreen.name) }) {
                        Icon(imageVector = Icons.Default.AccountBox, contentDescription = "")
                    }
                }

                    Column{
                        boldText(text = "Current Reading", size = 20)
                        Spacer(modifier = Modifier.height(10.dp))
                        if(viewModel.readingBookData.collectAsState().value.data != null){
                            if(viewModel.readingBookData.collectAsState().value.data?.size!! > 0) {
                                viewModel.readingBookData.collectAsState().value.data?.let { it1 ->
                                    currentBookList(
                                        it1,navController ,"reading")
                                }
                            }else{
                                Text("No Book found")
                            }
                        }else{
                            Text("No Book found")
                        }


                    }
                }


                    Column{
                        Spacer(modifier = Modifier.height(15.dp))
                        boldText(text = "Reading List", size = 20)
                        Spacer(modifier = Modifier.height(10.dp))
                        if(viewModel.data.collectAsState().value.data != null){
                            if(viewModel.data.collectAsState().value.data?.size!! > 0) {
                                viewModel.data.collectAsState().value.data?.let { it1 -> currentBookList(it1, navController ,"not Started") }
                            }else{
                                Text("No Book found")
                            }
                        }else{
                            Text("No Book found")
                        }


                    }
                }


            }

        }




@Composable
fun appBar(logoutFunc : () -> Unit){
    TopAppBar {
        Row(modifier = Modifier
            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
            Text("Home")
            IconButton(onClick = logoutFunc){
                Icon(imageVector = Icons.Default.Logout, contentDescription = "")
            }
        }
    }
}

@Composable
fun currentBookList(list : List<Book>, navController: NavController ,status :String){

    LazyRow{
        items(items = list){book ->
            currentBookCard(status, book = book){
                navController.navigate(NavigationEnum.UpdateBookScreen.name + "/" + book.id)
            }
        }
    }
}





@Composable
fun currentBookCard(status : String ,book : Book, onClick: () -> Unit){
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp

    Card(modifier = Modifier
        .width(screenWidth / 3)
        .clickable { onClick() }
        .padding(top = 5.dp, start = 5.dp, end = 2.dp), elevation = 5.dp, shape = RoundedCornerShape(10.dp)){
        Column {
            Row(modifier = Modifier.height(120.dp)){

                AsyncImage(model = book.imageUrl, contentDescription = "", modifier = Modifier.fillMaxWidth(0.6f))
                Spacer(modifier = Modifier.width(5.dp))
                Column(verticalArrangement = Arrangement.SpaceAround, modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally){
                    Icon(imageVector = Icons.Default.MonitorHeart, contentDescription = "")
                    Icon(imageVector = Icons.Default.Star, contentDescription = "")
                    Text(book.rating.toString(), fontSize = 10.sp)
                }
            }
            book.title?.let { boldText(text = it, size = 15) }
            Text(book.notes.toString(), fontSize = 10.sp)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.Bottom){
                Card(backgroundColor = Color.Blue){
                    Text(status, color=Color.White)
                }
            }
        }

    }
}

@Composable
fun fabContent(onClick : () -> Unit){
    FloatingActionButton(onClick, shape = CircleShape, backgroundColor = MaterialTheme.colors.primary) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "")
    }
}