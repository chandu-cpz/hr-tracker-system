package com.example.resume_parsing.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.resume_parsing.ui.screens.HomeScreen
import com.example.resume_parsing.ui.screens.LoginScreen
import com.example.resume_parsing.ui.screens.SignupScreen
import com.example.resume_parsing.ui.screens.SplashScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "splash") {
        composable("splash") {SplashScreen(navController = navController)}
        composable("login") { LoginScreen(navController) }
        composable("signup") { SignupScreen(navController) }
        composable("home") { HomeScreen(navController)}
    }
}
