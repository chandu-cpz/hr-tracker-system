
package com.example.resume_parsing.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.resume_parsing.ui.components.BottomNavigationBar
import com.example.resume_parsing.ui.screens.ApplicationDetailsScreen
import com.example.resume_parsing.ui.screens.ApplicationsListScreen
import com.example.resume_parsing.ui.screens.EmployeesListScreen
import com.example.resume_parsing.ui.screens.HrBottomNavigationBar
import com.example.resume_parsing.ui.screens.HrHomeScreen
import com.example.resume_parsing.ui.screens.JobOffersScreen
import com.example.resume_parsing.ui.screens.JobPostedListScreen
import com.example.resume_parsing.ui.screens.LoginScreen
import com.example.resume_parsing.ui.screens.Post_job
import com.example.resume_parsing.ui.screens.ProfileScreen
import com.example.resume_parsing.ui.screens.RecommendedJobsScreen
import com.example.resume_parsing.ui.screens.UserDashBoard
import com.example.resume_parsing.ui.screens.SignupScreen
import com.example.resume_parsing.ui.screens.SplashScreen
import com.example.resume_parsing.ui.screens.UserDashBoard
import com.example.resume_parsing.viewmodel.JobViewModel
import java.nio.charset.StandardCharsets

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
            startDestination = "user_dashboard",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("job_offers") { JobOffersScreen(bottomNavController) }
            composable("recommendations") {
                RecommendedJobsScreen(navController = navController, jobViewModel = JobViewModel())}
            composable("user_dashboard") { UserDashBoard(bottomNavController) }
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
            composable("applications_list") { ApplicationsListScreen(bottomNavController) }
            composable("employees_list") { EmployeesListScreen(bottomNavController) }
            composable("jobs_posted_list") { JobPostedListScreen(bottomNavController) }
            composable("applicationDetails/{applicationId}") { backStackEntry ->
                val applicationId = backStackEntry.arguments?.getString("applicationId")
                if (applicationId != null) {
                    ApplicationDetailsScreen(
                        applicationId = applicationId,
                        navController = navController
                    )
                } else {
                    Text("Error: Application ID not found") // Basic error handling
                }

            }
        }
    }
}
