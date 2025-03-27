package com.example.resume_parsing.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.material3.TextFieldDefaults // Already imported, good
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.resume_parsing.App
import com.example.resume_parsing.network.RetrofitClient
import com.example.resume_parsing.network.LoginRequest
import com.example.resume_parsing.network.UserResponse
import com.example.resume_parsing.utils.PreferencesHelper
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Define theme colors (using Color literals as requested, no new variables)
val NavyBlue = Color(0xFF003366) // Darker blue for background
val SkyBlue = Color(0xFF87CEEB)   // Lighter blue for accents/links
val BrightOrange = Color(0xFFFF8C00) // Vibrant orange for buttons/focus
val MutedOrange = Color(0xFFFFA500)  // Slightly less intense orange
val OffWhite = Color(0xFFF5F5F5)    // For text on dark background
val LightBlueGray = Color(0xFFB0BEC5) // For secondary text/unfocused elements

// Add this validation function at the top level
private fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NavyBlue), // Use NavyBlue for the main background
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title: "Resume Parser"
            Text(
                text = "Resume Parser",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = OffWhite, // Use OffWhite for better readability on dark blue
                modifier = Modifier.padding(top = 60.dp)
            )

            // Welcome note
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Welcome back! Please log in to continue.",
                fontSize = 18.sp,
                color = LightBlueGray, // Use LightBlueGray for less emphasis
                modifier = Modifier.padding(bottom = 40.dp)
            )

            // Email field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                colors = TextFieldDefaults.colors( // Use the new `colors` overload
                    focusedContainerColor = NavyBlue.copy(alpha = 0.1f), // Slightly tinted background when focused
                    unfocusedContainerColor = NavyBlue.copy(alpha = 0.05f), // Very subtle background when unfocused
                    focusedIndicatorColor = BrightOrange, // Orange indicator when focused
                    unfocusedIndicatorColor = SkyBlue.copy(alpha = 0.7f), // Lighter blue indicator when unfocused
                    cursorColor = BrightOrange, // Orange cursor
                    focusedTextColor = OffWhite,
                    unfocusedTextColor = OffWhite,
                    focusedLabelColor = BrightOrange, // Orange label when focused
                    unfocusedLabelColor = LightBlueGray // Light blue-gray label when unfocused
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                // Removed the separate .background modifier
            )

            // Password field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.colors( // Use the new `colors` overload
                    focusedContainerColor = NavyBlue.copy(alpha = 0.1f),
                    unfocusedContainerColor = NavyBlue.copy(alpha = 0.05f),
                    focusedIndicatorColor = BrightOrange, // Orange indicator
                    unfocusedIndicatorColor = SkyBlue.copy(alpha = 0.7f), // Lighter blue indicator
                    cursorColor = BrightOrange, // Orange cursor
                    focusedTextColor = OffWhite,
                    unfocusedTextColor = OffWhite,
                    focusedLabelColor = BrightOrange, // Orange label when focused
                    unfocusedLabelColor = LightBlueGray // Light blue-gray label when unfocused
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
                // Removed the separate .background modifier
            )

            // Login Button
            Button(
                onClick = {
                    when {
                        email.isEmpty() -> {
                            Toast.makeText(navController.context, "Email is required", Toast.LENGTH_SHORT).show()
                        }
                        !isValidEmail(email) -> {
                            Toast.makeText(navController.context, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                        }
                        password.isEmpty() -> {
                            Toast.makeText(navController.context, "Password is required", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            isLoading = true
                            val loginRequest = LoginRequest(email, password)
                            coroutineScope.launch {
                                RetrofitClient.apiService.loginUser(loginRequest).enqueue(object : Callback<UserResponse> {
                                    override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                                        isLoading = false
                                        if (response.isSuccessful) {
                                            val user = response.body()
                                            if (user != null ) {
                                                if (user.error == null) {
                                                    Toast.makeText(navController.context, "Login successful", Toast.LENGTH_SHORT).show()
                                                    PreferencesHelper.saveUserData(App.context, user)
                                                    if (user.role == "HR") {
                                                        navController.navigate("HrMainScreen")
                                                    } else {
                                                        navController.navigate("main")
                                                    }
                                                } else {
                                                    // Showing user.error might be better than response.message() if available
                                                    Toast.makeText(navController.context, "Login failed: ${user.error}", Toast.LENGTH_SHORT).show()
                                                }
                                            } else {
                                                 Toast.makeText(navController.context, "Login failed: Empty response body", Toast.LENGTH_SHORT).show()
                                            }
                                        } else {
                                            // Try to parse error body for more details if possible
                                            Toast.makeText(navController.context, "Login failed: ${response.code()} ${response.message()}", Toast.LENGTH_SHORT).show()
                                        }
                                    }

                                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                                        isLoading = false
                                        Log.d("LoginError", "Network request failed", t) // Log exception too
                                        Toast.makeText(navController.context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                                    }
                                })
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BrightOrange, // Orange background for button
                    contentColor = NavyBlue // Dark blue text for contrast on orange
                ),
                enabled = !isLoading // Disable button when loading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = NavyBlue, // Use dark blue for spinner on orange button
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Login", fontWeight = FontWeight.Bold) // Text color is set by contentColor above
                }
            }

            // Sign up navigation link
            TextButton(onClick = { navController.navigate("signup") }) {
                Text("Don't have an account? Sign Up", color = SkyBlue) // Use SkyBlue for the link
            }
        }

        // Optional: Loading indicator overlay
        if (isLoading) {
            CircularProgressIndicator(color = BrightOrange) // Orange spinner on the main background
        }
    }
}