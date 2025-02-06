package com.example.resume_parsing.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.text.font.FontWeight
import com.example.resume_parsing.network.RetrofitClient
import com.example.resume_parsing.network.UserSignUpRequest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Male") }
    var role by remember { mutableStateOf("User") }
    var company by remember { mutableStateOf("") }
    var companyImage by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF2E2E2E), Color(0xFF1A1A1A))))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // App name at the top with custom style
            Text(
                text = "Resume Parser",
                fontSize = 36.sp,
                color = Color.Cyan,
                fontWeight = FontWeight.Bold ,
                modifier = Modifier.padding(top = 60.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Welcome text
            Text(
                text = "Create your account",
                fontSize = 20.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Full Name input
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Cyan,
                    unfocusedIndicatorColor = Color.Gray,
                    cursorColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),
//                textStyle = TextStyle(color = Color.White),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Email input
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Cyan,
                    unfocusedIndicatorColor = Color.Gray,
                    cursorColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),
//                textStyle = TextStyle(color = Color.White),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Password input
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Cyan,
                    unfocusedIndicatorColor = Color.Gray,
                    cursorColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),
//                textStyle = TextStyle(color = Color.White),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Gender Radio Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Gender", color = Color.White, fontSize = 16.sp)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically // Align radio button and text vertically
            ) {
                Row(
                    modifier = Modifier.padding(end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = gender == "Male",
                        onClick = { gender = "Male" },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.Cyan,
                            unselectedColor = Color.Gray
                        )
                    )
                    Text("Male", color = Color.White)
                }

                Row(
                    modifier = Modifier.padding(end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = gender == "Female",
                        onClick = { gender = "Female" },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.Cyan,
                            unselectedColor = Color.Gray
                        )
                    )
                    Text("Female", color = Color.White)
                }

                Row(
                    modifier = Modifier.padding(end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = gender == "Other",
                        onClick = { gender = "Other" },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.Cyan,
                            unselectedColor = Color.Gray
                        )
                    )
                    Text("Other", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

// Role Radio Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Role", color = Color.White, fontSize = 16.sp)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically // Align radio button and text vertically
            ) {
                Row(
                    modifier = Modifier.padding(end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = role == "User",
                        onClick = { role = "User" },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.Cyan,
                            unselectedColor = Color.Gray
                        )
                    )
                    Text("User", color = Color.White)
                }

                Row(
                    modifier = Modifier.padding(end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = role == "HR",
                        onClick = { role = "HR" },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.Cyan,
                            unselectedColor = Color.Gray
                        )
                    )
                    Text("HR", color = Color.White)
                }
            }


            Spacer(modifier = Modifier.height(8.dp))

            // Company input
            OutlinedTextField(
                value = company,
                onValueChange = { company = it },
                label = { Text("Company") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Cyan,
                    unfocusedIndicatorColor = Color.Gray,
                    cursorColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),
//                textStyle = TextStyle(color = Color.White),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Company Image URL input
            OutlinedTextField(
                value = companyImage,
                onValueChange = { companyImage = it },
                label = { Text("Company Image URL") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Cyan,
                    unfocusedIndicatorColor = Color.Gray,
                    cursorColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),
//                textStyle = TextStyle(color = Color.White),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Sign Up Button
            Button(
                onClick = {
                    if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                        isLoading = true
                        val userSignUpRequest = UserSignUpRequest(
                            fullName = name,
                            email = email,
                            password = password,
                            gender = gender,
                            role = role,
                            company = company,
                            companyImage = companyImage
                        )

                        coroutineScope.launch {
                            try {
                                val response = RetrofitClient.apiService.signUp(userSignUpRequest)
                                if (response.isSuccessful) {
                                    val user = response.body()
                                    if (user != null ) {
                                        Toast.makeText(navController.context, "Sign up failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(navController.context, "Sign up failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                    Toast.makeText(navController.context, "Sign up failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                                }
                                } catch (e: Exception) {
                                Toast.makeText(navController.context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                            } finally {
                                isLoading = false
                            }
                        }
                    } else {
                        Toast.makeText(navController.context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.Cyan)
            ) {
                Text("Sign Up", color = Color.Black)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Navigation to Login Screen
            TextButton(onClick = { navController.navigate("login") }) {
                Text("Already have an account? Login", color = Color.Cyan)
            }
        }
    }
}

