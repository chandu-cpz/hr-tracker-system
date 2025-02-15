package com.example.resume_parsing.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

import com.example.resume_parsing.network.PostJobRequest
import com.example.resume_parsing.network.RetrofitClient
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Post_job(navController: NavController) {

    var jobTitle by remember { mutableStateOf("") }
    var jobDescription by remember { mutableStateOf("") }
    var companyName by remember { mutableStateOf("") }
    var responsibilities by remember { mutableStateOf("") }
    var qualifications by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var salary by remember { mutableStateOf("") }
    var skills by remember { mutableStateOf("") }
    var noOfPosts by remember { mutableStateOf("") }
    var jobType by remember { mutableStateOf("") }

    val jobTypeOptions = listOf("Full Time", "Part Time", "Contract", "Internship")
    var expandedJobType by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2C3E50)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Post Job",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            TextField(
                value = jobTitle,
                onValueChange = { jobTitle = it },
                label = { Text("Job Title", color = Color.White) },
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Cyan,
                    unfocusedIndicatorColor = Color.Gray,
                    cursorColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),  // Rounded corners
                singleLine = true          // Single line
            )

            TextField(
                value = jobDescription,
                onValueChange = { jobDescription = it },
                label = { Text("Job Description", color = Color.White) },
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Cyan,
                    unfocusedIndicatorColor = Color.Gray,
                    cursorColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),  // Rounded corners
                singleLine = true          // Single line
            )

            TextField(
                value = companyName,
                onValueChange = { companyName = it },
                label = { Text("Company Name", color = Color.White) },
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Cyan,
                    unfocusedIndicatorColor = Color.Gray,
                    cursorColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),  // Rounded corners
                singleLine = true          // Single line
            )

            TextField(
                value = qualifications,
                onValueChange = { qualifications = it },
                label = { Text("Qualifications", color = Color.White) },
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Cyan,
                    unfocusedIndicatorColor = Color.Gray,
                    cursorColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),  // Rounded corners
                singleLine = true          // Single line
            )

            TextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location", color = Color.White) },
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Cyan,
                    unfocusedIndicatorColor = Color.Gray,
                    cursorColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),  // Rounded corners
                singleLine = true          // Single line
            )

            TextField(
                value = salary,
                onValueChange = { salary = it },
                label = { Text("Salary", color = Color.White) },
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Cyan,
                    unfocusedIndicatorColor = Color.Gray,
                    cursorColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),  // Rounded corners
                singleLine = true          // Single line
            )

            TextField(
                value = skills,
                onValueChange = { skills = it },
                label = { Text("Skills", color = Color.White) },
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Cyan,
                    unfocusedIndicatorColor = Color.Gray,
                    cursorColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),  // Rounded corners
                singleLine = true          // Single line
            )

            TextField(
                value = noOfPosts,
                onValueChange = { noOfPosts = it },
                label = { Text("No Of Posts", color = Color.White) },
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Cyan,
                    unfocusedIndicatorColor = Color.Gray,
                    cursorColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),  // Rounded corners
                singleLine = true          // Single line
            )

            TextField(
                value = responsibilities,
                onValueChange = { responsibilities = it },
                label = { Text("Responsibilities", color = Color.White) },
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Cyan,
                    unfocusedIndicatorColor = Color.Gray,
                    cursorColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),  // Rounded corners
                singleLine = true          // Single line
            )

            Button(
                onClick = {
                    val jobData = PostJobRequest(
                        jobTitle = jobTitle,
                        jobDescription = jobDescription,
                        companyName = companyName,
                        responsibilities = responsibilities,
                        qualifications = qualifications,
                        location = location,
                        salary = salary.toIntOrNull() ?: 0,
                        skills = skills.split(",").map { it.trim() },
                        jobType = jobType,
                        noOfPosts = noOfPosts.toIntOrNull() ?: 1
                    )

                    coroutineScope.launch {
                        try {
                            val response = RetrofitClient.apiService.postJob(jobData)
                            if (response.isSuccessful) {
                                Toast.makeText(context, "Job posted successfully!", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Error posting job: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF3498DB))
            ) {
                Text("Post Job", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}