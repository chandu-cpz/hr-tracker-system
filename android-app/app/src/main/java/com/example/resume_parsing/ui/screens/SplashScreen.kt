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
import retrofit2.Response
@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) } // Show loading until we verify the token

    // LaunchedEffect to trigger side effects like network calls
    LaunchedEffect(Unit) {
        val token = PreferencesHelper.getToken(context) // Retrieve the saved token

        if (token != null) {
            // Try to auto-login by sending the token to the server for validation
            val response = try {
                RetrofitClient.apiService.AutoLogin()

            } catch (e: Exception) {
              null

            }
            Log.d("error_autologin",response.toString() );

            // If the response is successful, navigate to the main screen
            if (response?.isSuccessful == true) {
                navController.navigate("home") // Replace with your target screen
            } else {
                navController.navigate("login") // Navigate to login if token is invalid or expired
                Log.d("error_autologin","errroorrr");
            }
            isLoading = false
        } else {
            // If no token is found, navigate to the login screen directly
            isLoading = false
            navController.navigate("login")
        }
    }

    // Show loading state while checking for the token
    if (isLoading) {
        // Show a loading spinner
        CircularProgressIndicator(modifier = Modifier.fillMaxSize()) // Show loading while verifying token
    }
}
