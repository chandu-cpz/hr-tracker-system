package com.example.resume_parsing.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.resume_parsing.network.RetrofitClient
import com.example.resume_parsing.utils.PreferencesHelper
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val token = PreferencesHelper.getToken(context)
        val role = PreferencesHelper.getUserData(context)?.role // Get the user's role from SharedPreferences

        if (token != null) {
            val response = try {
                RetrofitClient.apiService.AutoLogin()
            } catch (e: Exception) {
                null
            }

            Log.d("error_autologin", response.toString())

            if (response?.isSuccessful == true) {
                // Check the role from SharedPreferences and navigate accordingly
                if (role == "HR") {
                    navController.navigate("HrMainScreen") {
                        popUpTo("splash") { inclusive = true } // Clear splash from backstack
                    }
                } else {
                    navController.navigate("main") {
                        popUpTo("splash") { inclusive = true } // Clear splash from backstack
                    }
                }
            } else {
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true } // Clear splash from backstack
                }
                Log.d("error_autologin", "Error during auto-login, navigating to login screen")
            }
            isLoading = false
        } else {
            isLoading = false
            navController.navigate("login") {
                popUpTo("splash") { inclusive = true } // Clear splash from backstack
            }
        }
    }

    if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    }
}