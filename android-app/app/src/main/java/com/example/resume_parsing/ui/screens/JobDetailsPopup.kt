package com.example.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.draw.clip
import com.example.resume_parsing.network.Job
import com.example.resume_parsing.viewmodel.JobViewModel
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

//@OptIn(ExperimentalLayoutApi::class)
//@Composable
//fun JobDetailsPopup(job: Job, onClose: () -> Unit) {
//    Box(
//        modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.8f)),
//        contentAlignment = Alignment.Center
//    ) {
//        Card(
//            modifier = Modifier.fillMaxSize(0.9f),
//            shape = RoundedCornerShape(16.dp),
//            colors = CardDefaults.cardColors(containerColor = Color.White)
//        ) {
//            Column(modifier = Modifier.padding(16.dp)) {
//                Text(text = job.jobTitle, fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
//
//                // Company Info Box
//                JobInfoSection(job)
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // Job Details Sections
//                JobDetailSection("Job Description", job.jobDescription)
//                JobDetailSection("Responsibilities", job.responsibilities)
//                JobDetailSection("Qualifications", job.qualifications)
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // Required Skills
//                Text(text = "Required Skills", fontSize = 18.sp, fontWeight = FontWeight.Bold)
//                FlowRow(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 8.dp),
//                    horizontalArrangement = Arrangement.spacedBy(6.dp),
//                    verticalArrangement = Arrangement.spacedBy(6.dp) // Ensure proper vertical spacing
//                ) {
//                    job.skills.forEach { skill ->
//                        Box(
//                            modifier = Modifier
//                                .background(Color(0xFFFF9800), shape = RoundedCornerShape(50))
//                                .padding(horizontal = 10.dp, vertical = 4.dp)
//                        ) {
//                            Text(
//                                text = skill,
//                                fontSize = 12.sp,
//                                color = Color.Black
//                            )
//                        }
//                    }
//                }
//
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // Upload Resume Section
//                UploadResumeSection()
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // Buttons Row
//                ActionButtons(onClose)
//            }
//        }
//    }
//}
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JobDetailsPopup(job: Job, onClose: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.8f)),
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
                    .verticalScroll(rememberScrollState()) // Add vertical scroll
            ) {
                Text(text = job.jobTitle, fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))

                // Company Info Box
                JobInfoSection(job)

                Spacer(modifier = Modifier.height(16.dp))

                // Job Details Sections
                JobDetailSection("Job Description", job.jobDescription)
                JobDetailSection("Responsibilities", job.responsibilities)
                JobDetailSection("Qualifications", job.qualifications)

                Spacer(modifier = Modifier.height(16.dp))

                // Required Skills
                Text(text = "Required Skills", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp) // Ensure proper vertical spacing
                ) {
                    job.skills.forEach { skill ->
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

                Spacer(modifier = Modifier.height(16.dp))

                // Upload Resume Section
                UploadResumeSection()

                Spacer(modifier = Modifier.height(16.dp))

                // Buttons Row
                ActionButtons(onClose)
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
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
        Icon(icon, contentDescription = label, tint = Color(0xFFFF9800))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = value, fontSize = 16.sp)
    }
}

@Composable
fun JobDetailSection(title: String, content: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
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

@Composable
fun UploadResumeSection() {
    Column {
        Text(text = "Upload Your Resume", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .border(2.dp, Color.Black, shape = RoundedCornerShape(8.dp))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.AttachFile, contentDescription = "Upload", tint = Color.Gray, modifier = Modifier.size(32.dp))
        }
    }
}

@Composable
fun ActionButtons(onClose: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = { /* Save job for later */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
        ) {
            Text(text = "Save Job for Later", color = Color.White)
        }

        Button(
            onClick = { /* Apply for Job */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
        ) {
            Text(text = "Apply for Job", color = Color.White)
        }
    }
}

fun formatDateTime(dateString: String): String {
    return try {
        // Parse the date string into a ZonedDateTime
        val zonedDateTime = ZonedDateTime.parse(dateString)
        // Define the desired output format (date and time)
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm")
        // Format the date and time
        zonedDateTime.format(formatter)
    } catch (e: Exception) {
        // Fallback in case of parsing error
        "Invalid Date"
    }
}