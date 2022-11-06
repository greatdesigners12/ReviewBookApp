package com.example.reviewbookapp.navigation

import java.lang.IllegalArgumentException

enum class NavigationEnum {
    AppBookListScreen,
    AppDetailsScreen,
    AppLoginScreen,
    AppMainScreen,
    AppRegisterScreen,
    AppReviewBookScreen,
    AppUserStatsScreen,
    AppSplashScreen,
    UpdateBookScreen;
    companion object{
        fun fromRoute(route : String) : NavigationEnum {
            return when(route?.substringBefore("/")){
                AppBookListScreen.name -> AppBookListScreen
                AppDetailsScreen.name -> AppDetailsScreen
                AppLoginScreen.name -> AppLoginScreen
                AppMainScreen.name -> AppMainScreen
                AppRegisterScreen.name -> AppRegisterScreen
                AppReviewBookScreen.name -> AppReviewBookScreen
                AppUserStatsScreen.name -> AppUserStatsScreen
                AppSplashScreen.name -> AppSplashScreen
                null -> AppLoginScreen
                else -> throw IllegalArgumentException("Route is not found")
            }
        }
    }
}