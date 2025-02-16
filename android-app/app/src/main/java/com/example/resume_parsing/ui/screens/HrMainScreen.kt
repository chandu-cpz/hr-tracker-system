
package com.example.resume_parsing.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.resume_parsing.ui.screens.DashboardCard
import com.example.resume_parsing.ui.screens.JobList
import com.example.resume_parsing.utils.PreferencesHelper
import com.example.resume_parsing.viewmodel.DashboardViewModel
import com.example.resume_parsing.viewmodel.JobListViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight


@Composable
fun HrHomeScreen(navController: NavController) {
    val context = LocalContext.current
    val dashboardViewModel: DashboardViewModel = viewModel()
    val jobListViewModel: JobListViewModel = viewModel()
    val userRole = PreferencesHelper.getUserData(context)?.role
    val userId = PreferencesHelper.getUserData(context)?._id

    LaunchedEffect(key1 = userId) {
        if (userRole != null) {
            if (userId != null) {
                dashboardViewModel.fetchApplicationCounts(userId, userRole)
            }
        }
        if (userId != null) {
            jobListViewModel.fetchJobs(userId)
        }
    }

    val applicationCounts = dashboardViewModel.applicationCounts.collectAsState()
    val jobs = jobListViewModel.jobs.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "HR Dashboard", fontSize = 24.sp)

        // Dashboard Cards
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            DashboardCard(
                title = "Applications",
                count = applicationCounts.value?.applications ?: 0,
                onClick = { navController.navigate("applications_list") }
            )
            DashboardCard(
                title = "Employees",
                count = applicationCounts.value?.employees ?: 0,
                onClick = { navController.navigate("employees_list") }
            )
            DashboardCard(
                title = "Jobs Open",
                count = jobs.value.size,
                onClick = { navController.navigate("jobs_posted_list") }
            )
        }

        // Pie Charts
        if (applicationCounts.value != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Application Status Pie Chart
                PieChart(
                    accepted = applicationCounts.value!!.accepted.toFloat(),
                    pending = applicationCounts.value!!.applications.toFloat(),
                    rejected = applicationCounts.value!!.rejected.toFloat(),
                    chartTitle = "Application Status"
                )

                // Add spacing between the charts
                Spacer(modifier = Modifier.height(16.dp))

                // Gender Distribution Pie Chart
                PieChart(
                    male = applicationCounts.value!!.male.toFloat(),
                    female = applicationCounts.value!!.female.toFloat(),
                    others = applicationCounts.value!!.others.toFloat(),
                    chartTitle = "Gender Distribution"
                )
            }
        }

    }
}
@Composable
fun PieChart(
    accepted: Float = 0f,
    pending: Float = 0f,
    rejected: Float = 0f,
    male: Float = 0f,
    female: Float = 0f,
    others: Float = 0f,
    chartTitle: String
) {
    val total = if (male == 0f && female == 0f) {
        accepted + pending + rejected
    } else {
        male + female + others
    }

    val allZero = total == 0f
    val acceptedPercentage = if (male == 0f && female == 0f) (accepted / total) else 0f
    val pendingPercentage = if (male == 0f && female == 0f) (pending / total) else 0f
    val rejectedPercentage = if (male == 0f && female == 0f) (rejected / total) else 0f
    val malePercentage = if (male != 0f || female != 0f) (male / total) else 0f
    val femalePercentage = if (male != 0f || female != 0f) (female / total) else 0f
    val othersPercentage = if (male != 0f || female != 0f) (others / total) else 0f


    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = chartTitle, fontSize = 16.sp, fontWeight = FontWeight.Bold)

        Row(verticalAlignment = Alignment.CenterVertically) {
            // Pie Chart
            Column(
                modifier = Modifier.width(100.dp)
            ) {
                if (allZero) {
                    // Display a uniform color pie chart if all values are zero
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                    )
                } else {
                    if (male == 0f && female == 0f) {
                        PieChartSection(
                            percentage = acceptedPercentage,
                            color = Color.Green
                        )
                        PieChartSection(
                            percentage = pendingPercentage,
                            color = Color.Blue
                        )
                        PieChartSection(
                            percentage = rejectedPercentage,
                            color = Color.Red
                        )
                    } else {
                        PieChartSection(
                            percentage = malePercentage,
                            color = Color.Blue
                        )
                        PieChartSection(
                            percentage = femalePercentage,
                            color = Color.Red
                        )
                        PieChartSection(
                            percentage = othersPercentage,
                            color = Color.Yellow
                        )
                    }
                }
            }

            // Legend
            Column(modifier = Modifier.padding(start = 8.dp)) {
                if (allZero) {
                    LegendItem(color = Color.Gray, text = "No Data")
                }
                else{
                    if (male == 0f && female == 0f) {
                        LegendItem(color = Color.Green, text = "Accepted")
                        LegendItem(color = Color.Blue, text = "Pending")
                        LegendItem(color = Color.Red, text = "Rejected")
                    } else {
                        LegendItem(color = Color.Blue, text = "Male")
                        LegendItem(color = Color.Red, text = "Female")
                        LegendItem(color = Color.Yellow, text = "Others")
                    }
                }
            }
        }
    }
}


@Composable
fun PieChartSection(percentage: Float, color: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth(percentage)
            .height(10.dp)
            .background(color)
    )
}

@Composable
fun LegendItem(color: Color, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(color)
        )
        Text(
            text = text,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}