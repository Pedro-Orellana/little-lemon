package com.pedroapps.littlelemon

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavigationComposable(navController : NavHostController) {
    val context = LocalContext.current
    val preferences = context.getSharedPreferences("Little Lemon", Context.MODE_PRIVATE)

    val initialDestination = if(preferences.contains("firstName")) "Home" else "Onboarding"

    NavHost(navController = navController, startDestination = initialDestination ) {
        composable(route = "Onboarding") {
            Onboarding(navController = navController, preferences = preferences)
        }

        composable(route = "Home") {
            Home(navController = navController)
        }
        composable(route = "Profile") {
            Profile(navController = navController, preferences = preferences)
        }
    }
}