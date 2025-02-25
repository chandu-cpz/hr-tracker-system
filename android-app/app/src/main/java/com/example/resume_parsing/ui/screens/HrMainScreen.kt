
package com.example.resume_parsing.ui.screens

import android.util.Log
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

import com.example.resume_parsing.utils.PreferencesHelper
import com.example.resume_parsing.viewmodel.DashboardViewModel
import com.example.resume_parsing.viewmodel.JobListViewModel
import com.example.resume_parsing.network.RetrofitClient


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
//    val response_job = RetrofitClient.apiService.JobOpenCounter()
//    Log.d("res", response_job.toString())

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
                count = 0,
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
