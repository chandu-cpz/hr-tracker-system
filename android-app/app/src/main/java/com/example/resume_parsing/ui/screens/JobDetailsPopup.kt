package com.example.app.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.resume_parsing.network.*
import com.example.resume_parsing.utils.PreferencesHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.foundation.text.ClickableText

@Composable
fun JobDetailsPopup(job: Job, onClose: () -> Unit) {
    //Local variables
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var isBookmarked by rememberSaveable { mutableStateOf(false) }
    var atsScoreResponse by remember { mutableStateOf<AtsScoreResponse?>(null) }
    var isLoadingAts by remember { mutableStateOf(false) }
    var isApplyingJob by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.8f)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxSize(0.9f),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Title
                Text(
                    text = job.jobTitle,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Company Info Section
                JobInfoSection(job)

                Spacer(modifier = Modifier.height(16.dp))

                // Job Details Section
                JobDetailSection("Job Description", job.jobDescription)
                JobDetailSection("Responsibilities", job.responsibilities)
                JobDetailSection("Qualifications", job.qualifications)

                Spacer(modifier = Modifier.height(16.dp))

                // Skills Section
                Text(text = "Required Skills", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                SkillsFlowRow(job.skills)

                Spacer(modifier = Modifier.height(16.dp))

                //Action Buttons and feedback
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            coroutineScope.launch(Dispatchers.IO) {
                                try {
                                    val apiService = RetrofitClient.apiService
                                    val userId = PreferencesHelper.getUserData(context)?._id // Get userId
                                    if (userId != null) {
                                        isLoadingAts = true
                                        //API request object
                                        val atsRequest = AtsRequest(jobId = job._id, userId = userId) // Send job ID and user ID in body
                                        val response = apiService.calculateAtsScore(atsRequest = atsRequest)
                                        if (response.isSuccessful) {
                                            atsScoreResponse = response.body() // Update the object
                                            println("Successfully Calculated ATS Score")
                                        } else {
                                            println("Exception while calling the function")
                                            CoroutineScope(Dispatchers.Main).launch {
                                                Toast.makeText(context, "Failed to calculate ATS score", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    } else {
                                        CoroutineScope(Dispatchers.Main).launch {
                                            Toast.makeText(context, "User ID not found", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                } catch (e: Exception) {
                                    CoroutineScope(Dispatchers.Main).launch {
                                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                                } finally {
                                    isLoadingAts = false
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
                        enabled = !isLoadingAts
                    ) {
                        Text(text = if (isLoadingAts) "Calculating..." else "Get Application Score", color = Color.White)
                    }

                    // Display Feedback
                    AtsFeedbackArea(atsScoreResponse)
                }

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            coroutineScope.launch(Dispatchers.IO) {
                                try {
                                    val apiService = RetrofitClient.apiService
                                    if (isBookmarked) {
                                        val response = apiService.deleteJob(job._id)
                                        if (response.isSuccessful) {
                                            isBookmarked = false
                                            //  removeSavedJob(job._id) // Local State update logic
                                            CoroutineScope(Dispatchers.Main).launch {
                                                Toast.makeText(context, "Job unsaved", Toast.LENGTH_SHORT).show()
                                            }
                                        } else {
                                            CoroutineScope(Dispatchers.Main).launch {
                                                Toast.makeText(context, "Failed to unsave job", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    } else {
                                        val jobIdRequest = JobIdRequest(jobId = job._id)
                                        val response = apiService.saveJob(jobIdRequest)
                                        if (response.isSuccessful) {
                                            isBookmarked = true
                                            //     addSavedJob(job._id) // Logic of Local Update
                                            CoroutineScope(Dispatchers.Main).launch {
                                                Toast.makeText(context, "Job saved", Toast.LENGTH_SHORT).show()
                                            }
                                        } else {
                                            CoroutineScope(Dispatchers.Main).launch {
                                                Toast.makeText(context, "Failed to save job", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }
                                } catch (e: Exception) {
                                    CoroutineScope(Dispatchers.Main).launch {
                                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
                    ) {
                        Text(text = if (isBookmarked) "Unsave Job" else "Save Job", color = Color.White)
                    }

                    Button(
                        onClick = {
                            coroutineScope.launch(Dispatchers.IO) {
                                try {
                                    val apiService = RetrofitClient.apiService
                                    val userResume = PreferencesHelper.getUserData(context)?.resume
                                    if (userResume != null) {
                                        val applyJobRequest = ApplyJobRequest(jobId = job._id, resume = userResume)
                                        isLoadingAts = true
                                        val response = apiService.applyJob(applyJobRequest)
                                        if (response.isSuccessful) {
                                            // Handle successful application
                                            println("Successful application: ${response.body()}")
                                            CoroutineScope(Dispatchers.Main).launch {
                                                Toast.makeText(context, "Successfully Applied For Job", Toast.LENGTH_SHORT).show()
                                            }
                                        } else {
                                            // Handle application failure (log the error, show a message, etc.)
                                            println("Failed to apply for job. Error: ${response.errorBody()?.string()}")
                                            CoroutineScope(Dispatchers.Main).launch {
                                                Toast.makeText(context, "Failed to apply. Please try again.", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }
                                    else{
                                        CoroutineScope(Dispatchers.Main).launch {
                                            Toast.makeText(context, "Please upload your resume in the profile section to apply for the job", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                } catch (e: Exception) {
                                    CoroutineScope(Dispatchers.Main).launch {
                                        Toast.makeText(context, "Error applying job", Toast.LENGTH_SHORT).show()
                                    }
                                } finally {
                                    isLoadingAts = false
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
                        enabled = !isApplyingJob && !isLoadingAts
                    ) {
                        Text(text = if (isApplyingJob) "Applying..." else "Apply for Job", color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // On Close Button
                Button(onClick = onClose) {
                    Text("Close")
                }
            }
        }
    }
}

@Composable
fun JobInfoSection(job: Job) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            JobInfoRow(Icons.Filled.Business, "Company", job.companyName)
            JobInfoRow(Icons.Filled.LocationOn, "Location", job.location)
            job.updatedAt?.let { dateString ->
                val formattedDate = formatDateTime(dateString)
                JobInfoRow(Icons.Filled.CalendarToday, "Date Posted", formattedDate)
            }
            JobInfoRow(Icons.Filled.AttachMoney, "Salary", "$${job.salary}")
        }
    }
}

@Composable
fun JobInfoRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(icon, contentDescription = label, tint = Color(0xFFFF9800))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = value, fontSize = 16.sp)
    }
}

@Composable
fun JobDetailSection(title: String, content: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = content, fontSize = 14.sp)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SkillsFlowRow(skills: List<String>?) {
    if (skills != null) {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            skills.forEach { skill ->
                Box(
                    modifier = Modifier
                        .background(Color(0xFFFF9800), shape = RoundedCornerShape(50))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = skill,
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }
            }
        }
    } else {
        Text(text = "No skills listed.") // Or handle the null case as you see fit
    }
}

fun formatDateTime(dateString: String): String {
    return try {
        val zonedDateTime = ZonedDateTime.parse(dateString)
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm")
        zonedDateTime.format(formatter)
    } catch (e: Exception) {
        "Invalid Date"
    }
}

@Composable
fun AtsFeedbackArea(atsScoreResponse: AtsScoreResponse?) {
    if (atsScoreResponse != null) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "ATS Score: ${atsScoreResponse.atsScore}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Feedback:",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Quantifiable Results: ${atsScoreResponse.feedback.quantifiable_results}",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = "Skills Suggestion: ${atsScoreResponse.feedback.skills_suggestion}",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = "Keyword Suggestion: ${atsScoreResponse.feedback.keyword_suggestion}",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = "Missing Keywords: ${atsScoreResponse.feedback.missing_keywords}",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = "Missing Skills: ${atsScoreResponse.feedback.missing_skills}",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = "Future Improvements: ${atsScoreResponse.feedback.future_improvements}",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
    } else {
        Text(
            text = "",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
    }
}