package com.example.resume_parsing.ui.screens

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.resume_parsing.viewmodel.JobViewModel
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.grid.GridCells
import com.example.resume_parsing.network.Job
import com.example.app.ui.JobCard
import com.example.app.ui.JobDetailsPopup
@Composable
fun RecommendedJobsScreen(navController: NavHostController, jobViewModel: JobViewModel = viewModel()) {
    val recommendedJobs by jobViewModel.recommendedJobs //Get RecommendedJob object
    val isLoading by jobViewModel.recommendationsLoading
    val isError by jobViewModel.recommendationsError
    var selectedJob by remember { mutableStateOf<Job?>(null) } //Type of job not Any
    val listState = rememberLazyGridState()

    // Call the recommendations API when the screen is created
    LaunchedEffect(key1 = true) {
        jobViewModel.loadRecommendedJobs()
    }

    //Map RecommendedJob object to Job objects for the ui components
    val jobs = remember(recommendedJobs) {
        derivedStateOf {
            recommendedJobs.map { recommendedJob ->
                Job(
                    _id = recommendedJob._id,
                    jobTitle = recommendedJob.jobTitle,
                    jobDescription = recommendedJob.jobDescription,
                    companyName = recommendedJob.companyName,
                    responsibilities = recommendedJob.responsibilities,
                    qualifications = recommendedJob.qualifications,
                    location = recommendedJob.location,
                    jobtype = recommendedJob.jobtype,
                    noOfPosts = recommendedJob.noOfPosts,
                    salary = recommendedJob.salary,
                    isOpen = recommendedJob.isOpen,
                    skills = recommendedJob.skills,
                    createdAt = recommendedJob.createdAt,
                    updatedAt = recommendedJob.updatedAt,
                    appliedBy = recommendedJob.appliedBy,
                    postedBy = recommendedJob.postedBy,
                    experience = recommendedJob.experience
                )
            }
        }
    }.value // Get the List<Job>

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Title Text
        Text(
            text = "Recommended Jobs",
            fontSize = 24.sp,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp),
            fontWeight = FontWeight.Bold
        )

        // Job Grid Layout
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .padding(top = 60.dp)
                .fillMaxSize(),
            state = listState
        ) {
            items(jobs) { job ->
                JobCard(job = job, onClick =  { selectedJob = job }) //All parameters type of job object
            }

            // Loading Indicator at Bottom
            if (isLoading && jobs.isNotEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }
            }
        }

        // Job Details Popup
        selectedJob?.let { job ->
            JobDetailsPopup(job = job, onClose = { selectedJob = null })
        }

        // Error Message
        if (isError) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text("Failed to load recommended jobs. Please try again.", color = Color.White)
            }
        }

        if (isLoading && jobs.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }
    }
}