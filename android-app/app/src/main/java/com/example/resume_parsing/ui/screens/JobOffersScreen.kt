package com.example.resume_parsing.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.app.ui.JobCard
import com.example.app.ui.JobDetailsPopup
import com.example.resume_parsing.network.Job
import com.example.resume_parsing.viewmodel.JobViewModel

//@Composable
//fun JobOffersScreen(navController: NavHostController) {
//    val jobViewModel: JobViewModel = viewModel()
//    val jobs by jobViewModel.jobs
//    var selectedJob by remember { mutableStateOf<Job?>(null) }
//
//    Box(
//        modifier = Modifier.fillMaxSize()
//    ) {
//        LazyVerticalGrid(
//            columns = GridCells.Fixed(1),
//            contentPadding = PaddingValues(16.dp),
//            modifier = Modifier.padding(top = 50.dp)
//        ) {
//            items(jobs) { job ->
//                JobCard(job) { selectedJob = job }
//            }
//        }
//
//        selectedJob?.let { job ->
//            JobDetailsPopup(job) { selectedJob = null }
//        }
//    }
//}
@Composable
fun JobOffersScreen(navController: NavHostController) {
    val jobViewModel: JobViewModel = viewModel()
    val jobs by jobViewModel.jobs
    var selectedJob by remember { mutableStateOf<Job?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Title Text
        Text(
            text = "Job Offers",
            fontSize = 24.sp,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
        )

        // Job Grid Layout
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.padding(top = 60.dp)
        ) {
            items(jobs) { job ->
                JobCard(job) { selectedJob = job }
            }
        }

        // Job Details Popup
        selectedJob?.let { job ->
            JobDetailsPopup(job) { selectedJob = null }
        }
    }
}