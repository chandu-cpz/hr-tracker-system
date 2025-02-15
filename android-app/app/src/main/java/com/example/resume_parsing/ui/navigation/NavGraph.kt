
package com.example.resume_parsing.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.resume_parsing.ui.components.BottomNavigationBar
import com.example.resume_parsing.ui.screens.HrBottomNavigationBar
import com.example.resume_parsing.ui.screens.HrHomeScreen
import com.example.resume_parsing.ui.screens.JobOffersScreen
import com.example.resume_parsing.ui.screens.LoginScreen
import com.example.resume_parsing.ui.screens.Post_job
import com.example.resume_parsing.ui.screens.ProfileScreen
import com.example.resume_parsing.ui.screens.RecommendedJobsScreen
import com.example.resume_parsing.ui.screens.SignupScreen
import com.example.resume_parsing.ui.screens.SplashScreen

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("signup") { SignupScreen(navController) }
        composable("main") { MainScreen(navController) }
        composable("HrMainScreen") { HrMainScreen(navController) }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun MainScreen(navController: NavHostController) {
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(bottomNavController) }
    ) { paddingValues ->
        NavHost(
            bottomNavController,
            startDestination = "job_offers",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("recommended_jobs") { RecommendedJobsScreen(bottomNavController) }
            composable("job_offers") { JobOffersScreen(bottomNavController) }
            composable("profile") { ProfileScreen(navController) }
            composable("login") { LoginScreen(navController) }

        }
    }
}
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun HrMainScreen(navController: NavHostController) {
    val bottomNavController = rememberNavController()
    Scaffold(
        bottomBar = { HrBottomNavigationBar(bottomNavController) }
    ) { paddingValues ->
        NavHost(
            bottomNavController,
            startDestination = "DashBoard",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("DashBoard") { HrHomeScreen(bottomNavController) }
            composable("profile") { ProfileScreen(navController) }
            composable("login") { LoginScreen(navController) }
            composable("Post_job") { Post_job(bottomNavController) }
        }
    }
}
