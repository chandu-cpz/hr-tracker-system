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
    val cardWidth = (screenWidth - 32.dp) / 2 // Adjust for padding and fit two cards per row

    Box(
        modifier = Modifier
            .padding(8.dp)
            .width(cardWidth)
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Blue Top Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(Color(0xFF80D0FF), shape = RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Column {
                    // Date Posted
//                    job.updatedAt?.let {
//                        Text(text = it, fontSize = 12.sp, color = Color.Black.copy(alpha = 0.7f))
//                    }
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFE3F2FD), shape = RoundedCornerShape(50))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ){
                        job.updatedAt?.let { dateString ->
                            val formattedDate = formatDate(dateString)

                        Text(text =  formattedDate, fontSize = 12.sp, color = Color.Black.copy(alpha = 0.7f))
                    }
                    }

                    // Company Name
                    Text(
                        text = job.companyName,
                        fontSize = 16.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(12.dp)
                    )

                    // Job Title
                    Text(
                        text = job.jobTitle,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(12.dp)
                    )

                    // Skills
                    FlowRow(
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        job.skills.forEach { skill ->
                            Box(
                                modifier = Modifier
                                    .background(Color(0xFFE3F2FD), shape = RoundedCornerShape(50))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(text = skill, fontSize = 12.sp, color = Color.Black)
                            }
                        }
                    }
                }

                // Company Logo (Top Right Corner)
                AsyncImage(
                    model = job.postedBy?.companyImage,
                    contentDescription = "Company Logo",
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.TopEnd)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Salary (Bottom Left)
                Text(
                    text = "${'$'}${job.salary}/month",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                // Details Button (Bottom Right)
                Button(
                    onClick = onClick,
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text(text = "Details", color = Color.White)
                }
            }
        }
    }
}
fun formatDate(dateString: String): String {
    return try {
        // Parse the date string into a ZonedDateTime
        val zonedDateTime = ZonedDateTime.parse(dateString)
        // Define the desired output format
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        // Format the date
        zonedDateTime.format(formatter)
    } catch (e: Exception) {
        // Fallback in case of parsing error
        "Invalid Date"
    }
}
