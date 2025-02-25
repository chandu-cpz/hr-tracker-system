package com.example.resume_parsing.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.resume_parsing.utils.PreferencesHelper
import com.example.resume_parsing.viewmodel.DashboardViewModel
import com.example.resume_parsing.viewmodel.JobListViewModel


@Composable
fun UserDashBoard(navController: NavHostController) {
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
        Text(text = "User Dashboard", fontSize = 24.sp)

        // Dashboard Cards
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            DashboardCard(
                title = "Accepted",
                count = applicationCounts.value?.accepted ?: 0,
                onClick = {}
//                onClick = { navController.navigate("applications_list") }
            )
            DashboardCard(
                title = "Applications",
                count = applicationCounts.value?.applications ?: 0,
                onClick = {}
//                onClick = { navController.navigate("employees_list") }
            )
            DashboardCard(
                title = "Rejected",
                count = applicationCounts.value?.rejected ?: 0,
                onClick = {}
//                onClick = { navController.navigate("jobs_posted_list") }
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

            }
        }

    }
}


