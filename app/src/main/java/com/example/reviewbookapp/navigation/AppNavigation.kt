package com.example.reviewbookapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.reviewbookapp.repository.BooksRepository
import com.example.reviewbookapp.screens.AppSplashScreen
import com.example.reviewbookapp.screens.BookList.AppBookListScreen
import com.example.reviewbookapp.screens.BookList.BookListViewModel
import com.example.reviewbookapp.screens.Details.AppDetailsScreen
import com.example.reviewbookapp.screens.Details.DetailsScreenViewModel
import com.example.reviewbookapp.screens.Login.AppLoginScreen
import com.example.reviewbookapp.screens.Login.LoginScreenViewModel
import com.example.reviewbookapp.screens.MainScreen.AppMainScreen
import com.example.reviewbookapp.screens.MainScreen.MainScreenViewModel
import com.example.reviewbookapp.screens.Register.AppRegisterScreen
import com.example.reviewbookapp.screens.ReviewBook.AppReviewBookScreen
import com.example.reviewbookapp.screens.UpdateBook.UpdateBookScreen
import com.example.reviewbookapp.screens.UpdateBook.UpdateBookScreenViewModel
import com.example.reviewbookapp.screens.UserStats.AppUserStatsScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sentry.ISpan
import io.sentry.Sentry

@ExperimentalComposeUiApi
@Composable
fun AppNavigation(loginScreenViewModel: LoginScreenViewModel = viewModel(),
                  booksListViewModel : BookListViewModel = viewModel(),
                    detailsScreenViewModel: DetailsScreenViewModel = viewModel(),
                    mainScreenViewModel: MainScreenViewModel = viewModel(),
                    updateBookScreenViewModel: UpdateBookScreenViewModel = viewModel(),
                    span : ISpan? = Sentry.getSpan()) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavigationEnum.AppSplashScreen.name){
        composable(NavigationEnum.AppSplashScreen.name){
            AppSplashScreen(navController = navController)
        }

        composable(NavigationEnum.AppLoginScreen.name){
            AppLoginScreen(navController = navController,
                loginScreenViewModel = loginScreenViewModel
            )
        }

        composable(NavigationEnum.AppRegisterScreen.name){
            AppRegisterScreen(navController = navController)
        }

        composable(NavigationEnum.AppMainScreen.name){
            mainScreenViewModel.getAllSavedBooks()
            mainScreenViewModel.getAllReadingBooks()
            AppMainScreen(navController = navController, mainScreenViewModel)
        }

        composable(NavigationEnum.AppDetailsScreen.name + "/{bookId}", arguments = listOf(navArgument(name = "bookId"){
            type = NavType.StringType
        })){
            it.arguments?.getString("bookId").let {
                AppDetailsScreen(navController = navController, it.toString(), detailsScreenViewModel)
            }

        }

        composable(NavigationEnum.AppUserStatsScreen.name){
            AppUserStatsScreen(navController = navController)
        }

        composable(NavigationEnum.AppReviewBookScreen.name){
            AppReviewBookScreen(navController = navController)
        }

        composable(NavigationEnum.AppBookListScreen.name){
            AppBookListScreen(navController = navController, booksListViewModel)
        }

        composable(NavigationEnum.UpdateBookScreen.name + "/{docId}", arguments = listOf(navArgument(name = "docId"){
            type = NavType.StringType
        })){
            it.arguments?.getString("docId").let {

                UpdateBookScreen(navController = navController, updateBookScreenViewModel, it.toString())
            }

        }
    }
}