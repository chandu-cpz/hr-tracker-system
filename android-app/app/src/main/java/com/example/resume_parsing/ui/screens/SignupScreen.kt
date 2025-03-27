package com.example.resume_parsing.ui.screens

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState // Import needed for scrolling
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll // Import needed for scrolling
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
// import androidx.compose.ui.graphics.Brush // Removed Brush for solid color background
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.text.font.FontWeight
// import androidx.compose.ui.tooling.preview.Preview // Keep if needed, but not essential for the revamp
import androidx.core.net.toUri
import com.example.resume_parsing.network.RetrofitClient
import com.example.resume_parsing.network.UserSignUpRequest
import kotlinx.coroutines.launch
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import com.example.resume_parsing.utils.FileUploadUtil.uploadFile
import com.example.resume_parsing.utils.FileUtils.uriToFile
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add

// NO MORE COLOR VARIABLES DEFINED HERE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("M") } // Default to 'M' to match first radio button
    var role by remember { mutableStateOf("HR") }
    var company by remember { mutableStateOf("") }
    var companyImage by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    var isLoading by remember { mutableStateOf(false) }
    var isUploading by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let { imageUri ->
                selectedImageUri = imageUri
                coroutineScope.launch {
                    isUploading = true
                    try {
                        val file = uriToFile(imageUri, context)
                        if (file != null) {
                            val cloudinaryUrl = uploadFile(context, file.toUri(), "company")
                            if (cloudinaryUrl != null) {
                                companyImage = cloudinaryUrl.secure_url
                                Toast.makeText(context, "Image Uploaded", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Upload Failed", Toast.LENGTH_SHORT).show()
                                selectedImageUri = null
                            }
                        } else {
                            Toast.makeText(context, "Could not create file from URI", Toast.LENGTH_SHORT).show()
                            selectedImageUri = null
                        }
                    } catch (e: Exception) {
                        Log.e("SignupScreen", "Error uploading image", e)
                        Toast.makeText(context, "Error uploading: ${e.message}", Toast.LENGTH_SHORT).show()
                        selectedImageUri = null
                    } finally {
                        isUploading = false
                    }
                }
            }
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF003366)) // Direct use: NavyBlue
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // App name
            Text(
                text = "Resume Parser",
                fontSize = 32.sp,
                color = Color(0xFFF5F5F5), // Direct use: OffWhite
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 60.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtitle text
            Text(
                text = "Create your account",
                fontSize = 18.sp,
                color = Color(0xFFB0BEC5) // Direct use: LightBlueGray
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Full Name input
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF003366).copy(alpha = 0.1f), // Direct use: NavyBlue
                    unfocusedContainerColor = Color(0xFF003366).copy(alpha = 0.05f),// Direct use: NavyBlue
                    focusedIndicatorColor = Color(0xFFFF8C00), // Direct use: BrightOrange
                    unfocusedIndicatorColor = Color(0xFF87CEEB).copy(alpha = 0.7f), // Direct use: SkyBlue
                    cursorColor = Color(0xFFFF8C00), // Direct use: BrightOrange
                    focusedTextColor = Color(0xFFF5F5F5), // Direct use: OffWhite
                    unfocusedTextColor = Color(0xFFF5F5F5), // Direct use: OffWhite
                    focusedLabelColor = Color(0xFFFF8C00), // Direct use: BrightOrange
                    unfocusedLabelColor = Color(0xFFB0BEC5) // Direct use: LightBlueGray
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email input
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                 colors = TextFieldDefaults.colors( // Reuse the themed colors directly
                    focusedContainerColor = Color(0xFF003366).copy(alpha = 0.1f),
                    unfocusedContainerColor = Color(0xFF003366).copy(alpha = 0.05f),
                    focusedIndicatorColor = Color(0xFFFF8C00),
                    unfocusedIndicatorColor = Color(0xFF87CEEB).copy(alpha = 0.7f),
                    cursorColor = Color(0xFFFF8C00),
                    focusedTextColor = Color(0xFFF5F5F5),
                    unfocusedTextColor = Color(0xFFF5F5F5),
                    focusedLabelColor = Color(0xFFFF8C00),
                    unfocusedLabelColor = Color(0xFFB0BEC5)
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password input
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors( // Reuse the themed colors directly
                    focusedContainerColor = Color(0xFF003366).copy(alpha = 0.1f),
                    unfocusedContainerColor = Color(0xFF003366).copy(alpha = 0.05f),
                    focusedIndicatorColor = Color(0xFFFF8C00),
                    unfocusedIndicatorColor = Color(0xFF87CEEB).copy(alpha = 0.7f),
                    cursorColor = Color(0xFFFF8C00),
                    focusedTextColor = Color(0xFFF5F5F5),
                    unfocusedTextColor = Color(0xFFF5F5F5),
                    focusedLabelColor = Color(0xFFFF8C00),
                    unfocusedLabelColor = Color(0xFFB0BEC5)
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Gender Radio Buttons Section
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Gender", color = Color(0xFFB0BEC5), fontSize = 16.sp) // Direct use: LightBlueGray
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Male Radio
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 24.dp)) {
                        RadioButton(
                            selected = gender == "M",
                            onClick = { gender = "M" },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color(0xFFFF8C00), // Direct use: BrightOrange
                                unselectedColor = Color(0xFF87CEEB) // Direct use: SkyBlue
                            )
                        )
                        Text("Male", color = Color(0xFFF5F5F5)) // Direct use: OffWhite
                    }
                    // Female Radio
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 24.dp)) {
                        RadioButton(
                            selected = gender == "F",
                            onClick = { gender = "F" },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color(0xFFFF8C00), // Direct use: BrightOrange
                                unselectedColor = Color(0xFF87CEEB) // Direct use: SkyBlue
                            )
                        )
                        Text("Female", color = Color(0xFFF5F5F5)) // Direct use: OffWhite
                    }
                    // Other Radio
                    Row(verticalAlignment = Alignment.CenterVertically) {
                         RadioButton(
                             selected = gender == "O",
                             onClick = { gender = "O" },
                             colors = RadioButtonDefaults.colors(
                                 selectedColor = Color(0xFFFF8C00), // Direct use: BrightOrange
                                 unselectedColor = Color(0xFF87CEEB) // Direct use: SkyBlue
                             )
                         )
                         Text("Other", color = Color(0xFFF5F5F5)) // Direct use: OffWhite
                    }
                }
            }


            Spacer(modifier = Modifier.height(24.dp))

            // Role Radio Buttons Section
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Role", color = Color(0xFFB0BEC5), fontSize = 16.sp) // Direct use: LightBlueGray
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // User Radio
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 24.dp)) {
                        RadioButton(
                            selected = role == "USER",
                            onClick = { role = "USER" },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color(0xFFFF8C00), // Direct use: BrightOrange
                                unselectedColor = Color(0xFF87CEEB) // Direct use: SkyBlue
                            )
                        )
                        Text("User", color = Color(0xFFF5F5F5)) // Direct use: OffWhite
                    }
                    // HR Radio
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = role == "HR",
                            onClick = { role = "HR" },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color(0xFFFF8C00), // Direct use: BrightOrange
                                unselectedColor = Color(0xFF87CEEB) // Direct use: SkyBlue
                            )
                        )
                        Text("HR", color = Color(0xFFF5F5F5)) // Direct use: OffWhite
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Conditional HR Fields
            if (role == "HR") {
                Spacer(modifier = Modifier.height(8.dp))

                // Company input
                OutlinedTextField(
                    value = company,
                    onValueChange = { company = it },
                    label = { Text("Company") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors( // Reuse the themed colors directly
                        focusedContainerColor = Color(0xFF003366).copy(alpha = 0.1f),
                        unfocusedContainerColor = Color(0xFF003366).copy(alpha = 0.05f),
                        focusedIndicatorColor = Color(0xFFFF8C00),
                        unfocusedIndicatorColor = Color(0xFF87CEEB).copy(alpha = 0.7f),
                        cursorColor = Color(0xFFFF8C00),
                        focusedTextColor = Color(0xFFF5F5F5),
                        unfocusedTextColor = Color(0xFFF5F5F5),
                        focusedLabelColor = Color(0xFFFF8C00),
                        unfocusedLabelColor = Color(0xFFB0BEC5)
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // File Upload Design
                Text("Company Logo", color = Color(0xFFB0BEC5), fontSize = 16.sp) // Direct use: LightBlueGray
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF87CEEB).copy(alpha = 0.2f)) // Direct use: SkyBlue
                        .clickable(enabled = !isUploading) { imagePickerLauncher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    if (isUploading) {
                        CircularProgressIndicator(
                            color = Color(0xFFFF8C00), // Direct use: BrightOrange
                            modifier = Modifier.size(40.dp)
                        )
                    } else if (selectedImageUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(selectedImageUri),
                            contentDescription = "Company Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add Image",
                            tint = Color(0xFFF5F5F5) // Direct use: OffWhite
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            } else {
                 Spacer(modifier = Modifier.height(24.dp))
            }

            // Sign Up Button
            Button(
                onClick = {
                    // Keep existing validation and signup logic
                    if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                        if (role == "HR" && (company.isEmpty() || companyImage.isEmpty())) {
                            Toast.makeText(context, "Please fill company details and upload company image for HR role.", Toast.LENGTH_LONG).show()
                            return@Button
                        }

                        isLoading = true
                        val userSignUpRequest = UserSignUpRequest(
                            fullName = name,
                            email = email,
                            password = password,
                            gender = gender,
                            role = role,
                            company = if (role == "HR") company else null,
                            companyImage = if (role == "HR") companyImage else null
                        )

                        coroutineScope.launch {
                            try {
                                val response = RetrofitClient.apiService.signUp(userSignUpRequest)
                                if (response.isSuccessful) {
                                    Toast.makeText(context, "Sign up successful!", Toast.LENGTH_SHORT).show()
                                    navController.navigate("login") { /* Optional popUpTo */ }
                                } else {
                                    val errorMsg = when (response.code()) {
                                        400 -> "Please check all fields."
                                        401 -> "Please use a stronger password."
                                        409 -> "This email address is already registered."
                                        else -> "Sign up failed (${response.code()}). Please try again."
                                    }
                                    Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show()
                                    Log.e("SignupError", "Code: ${response.code()}, Message: ${response.message()}, Body: ${response.errorBody()?.string()}")
                                }
                            } catch (e: Exception) {
                                Log.e("SignupError", "Network or other error", e)
                                Toast.makeText(context, "Error: ${e.localizedMessage ?: "Unknown error"}", Toast.LENGTH_LONG).show()
                            } finally {
                                isLoading = false
                            }
                        }
                    } else {
                        Toast.makeText(context, "Please fill all required fields (Name, Email, Password)", Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF8C00), // Direct use: BrightOrange
                    contentColor = Color(0xFF003366), // Direct use: NavyBlue
                    disabledContainerColor = Color(0xFFFFA500).copy(alpha = 0.6f), // Direct use: MutedOrange (approx)
                    disabledContentColor = Color(0xFF003366).copy(alpha = 0.7f) // Direct use: NavyBlue
                ),
                enabled = !isLoading && !isUploading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color(0xFF003366), // Direct use: NavyBlue
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Sign Up", fontWeight = FontWeight.Bold)
                }
            }

            // Navigation to Login Screen
            TextButton(
                onClick = { if (!isLoading && !isUploading) navController.navigate("login") },
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                Text("Already have an account? Login", color = Color(0xFF87CEEB)) // Direct use: SkyBlue
            }
        }
    }
}