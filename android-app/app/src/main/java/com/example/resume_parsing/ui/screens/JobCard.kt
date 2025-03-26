package com.example.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.compose.foundation.layout.FlowRow
import com.example.resume_parsing.network.Job
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JobCard(job: Job, onClick: () -> Unit) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cardWidth = (screenWidth - 32.dp) / 2

    Box(
        modifier = Modifier
            .padding(8.dp)
            .width(cardWidth)
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Blue Top Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .background(Color(0xFF80D0FF), shape = RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Column {
                    // Date Posted
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFE3F2FD), shape = RoundedCornerShape(50))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ){
                        job.updatedAt?.let { dateString ->
                            val formattedDate = formatDate(dateString)
                            Text(
                                text = formattedDate, 
                                fontSize = 13.sp, 
                                color = Color.Black.copy(alpha = 0.7f),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Company Name
                    Text(
                        text = job.companyName ?: "Company Name Unavailable",
                        fontSize = 17.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    // Job Title
                    Text(
                        text = job.jobTitle ?: "Job Title Unavailable",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Skills
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth(),  // Removed the top padding here
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        job.skills?.forEach { skill ->
                            Box(
                                modifier = Modifier
                                    .background(Color(0xFFE3F2FD), shape = RoundedCornerShape(50))
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = skill, 
                                    fontSize = 14.sp, 
                                    color = Color.Black,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }

                // Company Logo (Top Right Corner)
                AsyncImage(
                    model = job.postedBy?.companyImage,
                    contentDescription = "Company Logo",
                    modifier = Modifier
                        .size(56.dp)
                        .align(Alignment.TopEnd)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Salary (Bottom Left)
                Text(
                    text = "${'$'}${job.salary ?: "Salary Negotiable"}/month",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                // Details Button (Bottom Right)
                Button(
                    onClick = onClick,
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(
                        text = "Details", 
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

fun formatDate(dateString: String): String {
    return try {
        val zonedDateTime = ZonedDateTime.parse(dateString)
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        zonedDateTime.format(formatter)
    } catch (e: Exception) {
        "Invalid Date"
    }
}