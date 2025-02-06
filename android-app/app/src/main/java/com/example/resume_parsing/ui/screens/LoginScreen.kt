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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.resume_parsing.network.RetrofitClient
import com.example.resume_parsing.network.LoginRequest
import com.example.resume_parsing.network.UserResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            .background(Color(0xFF2C3E50)), // Dark blue background for the whole screen
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
                color = Color.White,
                modifier = Modifier.padding(top = 60.dp) // Padding from the top to center
            )

            // Welcome note
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Welcome back! Please log in to continue.",
                fontSize = 18.sp,
                color = Color(0xFFBDC3C7), // Light gray color for the welcome note
                modifier = Modifier.padding(bottom = 40.dp) // More space between the title and form
            )

            // Email field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFF3498DB), // Blue indicator
                    unfocusedIndicatorColor = Color(0xFFBDC3C7), // Gray indicator
                    cursorColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .background(Color(0xFF34495E)) // Dark background for the input field
            )

            // Password field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFF3498DB), // Blue indicator
                    unfocusedIndicatorColor = Color(0xFFBDC3C7), // Gray indicator
                    cursorColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
                    .background(Color(0xFF34495E)) // Dark background for the input field
            )

            // Login Button
            Button(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        isLoading = true
                        val loginRequest = LoginRequest(email, password)
                        coroutineScope.launch {
                            RetrofitClient.apiService.loginUser(loginRequest).enqueue(object : Callback<UserResponse> {
                                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                                    isLoading = false
                                    if (response.isSuccessful) {
                                        // Handle successful login, store user info or navigate
                                        val user = response.body()
                                        if (user != null ) {
                                            if (user.error == null) {
                                                Toast.makeText(navController.context, "Login successful", Toast.LENGTH_SHORT).show()
                                                // Navigate to the main screen or dashboard
                                                navController.navigate("home")  // Replace with your target screen
                                            }else {
                                                Toast.makeText(navController.context, "Login failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    } else {
                                        Toast.makeText(navController.context, "Login failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                                    }
                                }

                                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                                    isLoading = false
                                    Toast.makeText(navController.context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                                }
                            })
                        }
                    } else {
                        Toast.makeText(navController.context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF3498DB)) // Blue color for button
            ) {
                Text("Login", color = Color.White, fontWeight = FontWeight.Bold)
            }

            // Sign up navigation link
            TextButton(onClick = { navController.navigate("signup") }) {
                Text("Don't have an account? Sign Up", color = Color(0xFF3498DB)) // Blue color for link
            }
        }
    }
}


