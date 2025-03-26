package com.example.resume_parsing.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.resume_parsing.utils.PreferencesHelper
import com.example.resume_parsing.viewmodel.DashboardViewModel
import com.example.resume_parsing.viewmodel.JobListViewModel

@OptIn(ExperimentalMaterial3Api::class) // For Scaffold
@Composable
fun UserDashBoard(navController: NavHostController) {
    val context = LocalContext.current
    val dashboardViewModel: DashboardViewModel = viewModel()
    // val jobListViewModel: JobListViewModel = viewModel() // Not used in UI, commented out for now
    val userRole = PreferencesHelper.getUserData(context)?.role
    val userId = PreferencesHelper.getUserData(context)?._id

    // Fetch data when userId changes
    LaunchedEffect(key1 = userId) {
        if (userRole != null && userId != null) {
            dashboardViewModel.fetchApplicationCounts(userId, userRole)
        }
        // If jobListViewModel were used, fetch here:
        // if (userId != null) {
        //     jobListViewModel.fetchJobs(userId)
        // }
    }

    val applicationCountsState = dashboardViewModel.applicationCounts.collectAsState()
    val applicationCounts = applicationCountsState.value // Get the actual value

    // val jobs = jobListViewModel.jobs.collectAsState() // Not used in UI

    Scaffold( // Use Scaffold for basic screen structure
        topBar = {
            TopAppBar(
                title = { Text("User Dashboard") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background // Set background color
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize() // Fill the available size within Scaffold
                .padding(paddingValues) // Apply padding from Scaffold
                .padding(16.dp) // Add overall content padding
                .verticalScroll(rememberScrollState()) // Make content scrollable
        ) {
            // Dashboard Cards Section
            SectionTitle("Overview")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp), // Reduced vertical padding around row
                // Space cards evenly, including space at start/end
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Display cards if counts are available
                 applicationCounts?.let { counts ->
                    DashboardCard(
                        title = "Accepted",
                        count = counts.accepted ?: 0,
                        onClick = {
                            // Keep original empty onClick
                            // navController.navigate("applications_list/accepted") // Example potential navigation
                        }
                    )
                    DashboardCard(
                        title = "Applied", // Changed title for clarity? Or keep "Applications"
                        count = counts.applications ?: 0, // Assuming 'applications' means total applied
                        onClick = {
                             // Keep original empty onClick
                             // navController.navigate("applications_list/all") // Example potential navigation
                         }
                    )
                    DashboardCard(
                        title = "Rejected",
                        count = counts.rejected ?: 0,
                        onClick = {
                             // Keep original empty onClick
                             // navController.navigate("applications_list/rejected") // Example potential navigation
                         }
                    )
                } ?: run {
                    // Optional: Show placeholders or loading indicator while counts are null
                    // For now, the Row will just be empty or have less space
                    Text(
                        "Loading counts...",
                         style = MaterialTheme.typography.bodyMedium,
                         modifier = Modifier.padding(vertical = 30.dp) // Placeholder padding
                         )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Pie Charts Section (only if counts are not null)
            if (applicationCounts != null) {
                 SectionTitle("Application Status")
                PieChart(
                    accepted = applicationCounts.accepted.toFloat(),
                    // Assuming 'applications' is total, so pending = total - accepted - rejected
                    pending = (applicationCounts.applications - applicationCounts.accepted - applicationCounts.rejected).coerceAtLeast(0).toFloat(),
                    rejected = applicationCounts.rejected.toFloat(),
                    chartTitle = "" // Title is now handled by SectionTitle
                )
            } else {
                // Optional: Show placeholder if counts are null
                 Box(modifier = Modifier.fillMaxWidth().height(150.dp), contentAlignment = Alignment.Center){
                    Text("Loading chart data...")
                 }
            }

            // Add more sections or content here if needed

            // Spacer to push content up if screen is tall and content is short
            // This might not be strictly necessary with Column/Scroll but can help in some cases
            // Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
        color = MaterialTheme.colorScheme.onBackground // Color for text on main background
    )
}
