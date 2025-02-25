//package com.example.resume_parsing.ui.screens
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.lazy.grid.items
//import androidx.compose.material.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.FilterList
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import com.example.app.ui.JobCard
//import com.example.app.ui.JobDetailsPopup
//import com.example.resume_parsing.network.Job
//import com.example.resume_parsing.viewmodel.JobViewModel
//import androidx.compose.foundation.lazy.grid.rememberLazyGridState
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.ui.text.font.FontWeight
//
//@Composable
//fun JobOffersScreen(navController: NavHostController) {
//    val jobViewModel: JobViewModel = viewModel()
//    val jobs by jobViewModel.jobs
//    val isLoading by jobViewModel.isLoading
//    val isError by jobViewModel.isError
//    val totalPages by jobViewModel.totalPages
//    val page by jobViewModel.page
//    var selectedJob by remember { mutableStateOf<Job?>(null) }
//    val listState = rememberLazyGridState()
//    var showFilterDialog by remember { mutableStateOf(false) }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.Black)
//    ) {
//        // Title Text
//        Text(
//            text = "Job Offers",
//            fontSize = 24.sp,
//            color = Color.White,
//            modifier = Modifier
//                .align(Alignment.TopCenter)
//                .padding(top = 16.dp),
//            fontWeight = FontWeight.Bold
//        )
//
//        // Job Grid Layout
//        LazyVerticalGrid(
//            columns = GridCells.Fixed(1),
//            contentPadding = PaddingValues(16.dp),
//            modifier = Modifier
//                .padding(top = 60.dp)
//                .fillMaxSize(),
//            state = listState
//        ) {
//            items(jobs) { job ->
//                JobCard(job = job) { selectedJob = job }
//            }
//
//            // Loading Indicator at Bottom
//            if (isLoading && jobs.isNotEmpty()) {
//                item {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        CircularProgressIndicator(color = Color.White)
//                    }
//                }
//            }
//        }
//
//        // Detect when near the bottom and load more
//        LaunchedEffect(listState.layoutInfo) {
//            val visibleItemsInfo = listState.layoutInfo.visibleItemsInfo
//            if (!isLoading && !isError && jobs.isNotEmpty() && visibleItemsInfo.isNotEmpty() && page < totalPages) {
//                val lastVisibleItemIndex = visibleItemsInfo.last().index
//                val totalItemsCount = listState.layoutInfo.totalItemsCount
//                if (lastVisibleItemIndex >= totalItemsCount - 3) {
//                    jobViewModel.loadMoreJobs()
//                }
//            }
//        }
//
//        // Job Details Popup
//        selectedJob?.let { job ->
//            JobDetailsPopup(job) { selectedJob = null }
//        }
//
//        // Error Message
//        if (isError) {
//            Box(
//                contentAlignment = Alignment.Center,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(16.dp)
//            ) {
//                Text("Failed to load jobs. Please try again.", color = Color.White)
//            }
//        }
//
//        if (isLoading && jobs.isEmpty()) {
//            Box(
//                contentAlignment = Alignment.Center,
//                modifier = Modifier.fillMaxSize()
//            ) {
//                CircularProgressIndicator(color = Color.White)
//            }
//        }
//
//        // Floating Action Button
//        ExtendedFloatingActionButton(
//            onClick = { showFilterDialog = true },
//            modifier = Modifier
//                .align(Alignment.BottomEnd)
//                .padding(16.dp),
//            containerColor = MaterialTheme.colorScheme.primary,
//            elevation = FloatingActionButtonDefaults.elevation()
//        ) {
//            Icon(Icons.Filled.FilterList, "Filter", tint = Color.White)
//        }
//
//        // Filter Dialog
//        if (showFilterDialog) {
//            FilterDialog(jobViewModel = jobViewModel) {
//                showFilterDialog = false
//            }
//        }
//    }
//}
//
//@Composable
//fun FilterDialog(jobViewModel: JobViewModel, onDismiss: () -> Unit) {
//    var jobTitle by remember { mutableStateOf("") }
//    var location by remember { mutableStateOf("") }
//    var experience by remember { mutableStateOf("") }
//    var jobType by remember { mutableStateOf("") }
//    var minSalary by remember { mutableStateOf(0f) }
//    var maxSalary by remember { mutableStateOf(999999f) }
//    var selectedSalaryRange by remember { mutableStateOf(minSalary..maxSalary) }
//
//
//    AlertDialog(
//        onDismissRequest = { onDismiss() },
//        title = { Text("Filter Jobs", color = Color.Black) },
//        text = {
//            Column {
//                Dropdown(
//                    name = "Job Title",
//                    options = listOf("Software Engineer", "Data Scientist", "Product Manager"), // Replace with actual options
//                    onSelect = { name, value -> jobTitle = value },
//                    textStyle = TextStyle(color = Color.Black)
//                )
//                Dropdown(
//                    name = "Location",
//                    options = listOf("New York", "London", "San Francisco"), // Replace with actual options
//                    onSelect = { name, value -> location = value },
//                    textStyle = TextStyle(color = Color.Black)
//                )
//                Dropdown(
//                    name = "Experience",
//                    options = listOf("Entry Level", "Mid-Level", "Senior Level"), // Replace with actual options
//                    onSelect = { name, value -> experience = value },
//                    textStyle = TextStyle(color = Color.Black)
//                )
//
//                // Salary Range Selector
//                Column {
//                    Text("Salary", color = Color.Black)
//                    RangeSlider(
//                        valueRange = 0f..999999f,
//                        value = selectedSalaryRange,
//                        onValueChange = { selectedSalaryRange = it },
//                        onValueChangeFinished = {
//                            minSalary = selectedSalaryRange.start
//                            maxSalary = selectedSalaryRange.endInclusive
//                            jobViewModel.setMinSalary(minSalary.toInt().toString())
//                            jobViewModel.setMaxSalary(maxSalary.toInt().toString())
//                        }
//                    )
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.spacedBy(8.dp)
//                    ) {
//                        Text("Min:", color = Color.Black)
//                        OutlinedTextField(
//                            value = minSalary.toInt().toString(),
//                            onValueChange = {
//                                minSalary = it.toFloatOrNull() ?: 0f
//                            },
//                            textStyle = TextStyle(color = Color.Black)
//                        )
//                        Text("Max:", color = Color.Black)
//                        OutlinedTextField(
//                            value = maxSalary.toInt().toString(),
//                            onValueChange = {
//                                maxSalary = it.toFloatOrNull() ?: 999999f
//                            },
//                            textStyle = TextStyle(color = Color.Black)
//                        )
//                    }
//                }
//                Sidebar(onSelect = { selectedJobTypes ->
//                    jobViewModel.setJobType(selectedJobTypes.joinToString(","))
//                })
//
//            }
//        },
//        confirmButton = {
//            Button(onClick = {
//                jobViewModel.setJobTitle(if (jobTitle.isBlank()) null else jobTitle)
//                jobViewModel.setLocation(if (location.isBlank()) null else location)
//                jobViewModel.setExperience(if (experience.isBlank()) null else experience)
//                onDismiss()
//            }) {
//                Text("Apply", color = Color.White)
//            }
//        },
//        dismissButton = {
//            TextButton(onClick = { onDismiss() }) {
//                Text("Cancel", color = Color.Black)
//            }
//        },
//        containerColor = Color.White,
//        titleContentColor = Color.Black,
//        textContentColor = Color.Black
//    )
//}
//
//@Composable
//fun Dropdown(
//    name: String,
//    options: List<String>,
//    onSelect: (String, String) -> Unit,
//    textStyle: TextStyle
//) {
//    var expanded by remember { mutableStateOf(false) }
//    var selectedOptionText by remember { mutableStateOf("") }
//
//    Column {
//        Text(text = name, color = textStyle.color)
//        OutlinedButton(onClick = { expanded = true }) {
//            Text(text = selectedOptionText.ifEmpty { "Select $name" }, color = textStyle.color)
//        }
//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false }
//        ) {
//            options.forEach { option ->
//                DropdownMenuItem(
//                    text = { Text(text = option, color = textStyle.color) },
//                    onClick = {
//                        selectedOptionText = option
//                        onSelect(name, option)
//                        expanded = false
//                    }
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun Sidebar(
//    onSelect: (List<String>) -> Unit
//) {
//    val jobTypes = remember { listOf("Full-time", "Part-time", "Contract", "Internship") } // Replace with your actual job types
//    val checkedState = remember { mutableStateMapOf<String, Boolean>().apply {
//        jobTypes.forEach { this[it] = false } // Initialize all to false
//    } }
//
//    Column(
//        modifier = Modifier
//            .width(150.dp)
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally // Center content horizontally
//    ) {
//        Text(text = "Job Types", color = Color.Black, textAlign = TextAlign.Center)
//
//        jobTypes.forEach { jobType ->
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//            ) {
//                Checkbox(
//                    checked = checkedState[jobType] ?: false,
//                    onCheckedChange = { isChecked ->
//                        checkedState[jobType] = isChecked
//                        onSelect(checkedState.filterValues { it }.keys.toList()) // Send the list of checked job types
//                    }
//                )
//                Text(jobType, color = Color.Black)
//            }
//        }
//    }
//}

package com.example.resume_parsing.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.app.ui.JobCard
import com.example.app.ui.JobDetailsPopup
import com.example.resume_parsing.network.Job
import com.example.resume_parsing.viewmodel.JobViewModel
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.font.FontWeight


@Composable
fun JobOffersScreen(navController: NavHostController) {
    val jobViewModel: JobViewModel = viewModel()
    val jobs by jobViewModel.jobs
    val isLoading by jobViewModel.isLoading
    val isError by jobViewModel.isError
    val totalPages by jobViewModel.totalPages
    val page by jobViewModel.page
    var selectedJob by remember { mutableStateOf<Job?>(null) }
    val listState = rememberLazyGridState()
    var showFilterDialog by remember { mutableStateOf(false) }

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
                JobCard(job = job) { selectedJob = job }
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

        // Detect when near the bottom and load more
        LaunchedEffect(listState.layoutInfo) {
            val visibleItemsInfo = listState.layoutInfo.visibleItemsInfo
            if (!isLoading && !isError && jobs.isNotEmpty() && visibleItemsInfo.isNotEmpty() && page < totalPages) {
                val lastVisibleItemIndex = visibleItemsInfo.last().index
                val totalItemsCount = listState.layoutInfo.totalItemsCount
                if (lastVisibleItemIndex >= totalItemsCount - 3) {
                    jobViewModel.loadMoreJobs()
                }
            }
        }

        // Job Details Popup
        selectedJob?.let { job ->
            JobDetailsPopup(job) { selectedJob = null }
        }

        // Error Message
        if (isError) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text("Failed to load jobs. Please try again.", color = Color.White)
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

        // Floating Action Button - Filter
        ExtendedFloatingActionButton(
            onClick = { showFilterDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            elevation = FloatingActionButtonDefaults.elevation(),
            text = { Text("Filter", color = Color.White) },
            icon = { Icon(Icons.Filled.FilterList, "Filter", tint = Color.White) }
        )


        // Floating Action Button - Recommend Jobs
        ExtendedFloatingActionButton(
            onClick = { navController.navigate("recommendations") }, // Navigate to Recommendations screen
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.secondary, //  distinct color
            elevation = FloatingActionButtonDefaults.elevation(),
            text = { Text("Recommend", color = Color.White) },
            icon = { Icon(Icons.Filled.ThumbUp, "Recommend", tint = Color.White) }

        )

        // Filter Dialog
        if (showFilterDialog) {
            FilterDialog(jobViewModel = jobViewModel) {
                showFilterDialog = false
            }
        }
    }
}


@Composable
fun FilterDialog(jobViewModel: JobViewModel, onDismiss: () -> Unit) {
    var jobTitle by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    var jobType by remember { mutableStateOf("") }
    var minSalary by remember { mutableStateOf(0f) }
    var maxSalary by remember { mutableStateOf(999999f) }
    var selectedSalaryRange by remember { mutableStateOf(minSalary..maxSalary) }


    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Filter Jobs", color = Color.Black) },
        text = {
            Column {
                Dropdown(
                    name = "Job Title",
                    options = listOf("Software Engineer", "Data Scientist", "Product Manager"), // Replace with actual options
                    onSelect = { name, value -> jobTitle = value },
                    textStyle = TextStyle(color = Color.Black)
                )
                Dropdown(
                    name = "Location",
                    options = listOf("New York", "London", "San Francisco"), // Replace with actual options
                    onSelect = { name, value -> location = value },
                    textStyle = TextStyle(color = Color.Black)
                )
                Dropdown(
                    name = "Experience",
                    options = listOf("Entry Level", "Mid-Level", "Senior Level"), // Replace with actual options
                    onSelect = { name, value -> experience = value },
                    textStyle = TextStyle(color = Color.Black)
                )

                // Salary Range Selector
                Column {
                    Text("Salary", color = Color.Black)
                    RangeSlider(
                        valueRange = 0f..999999f,
                        value = selectedSalaryRange,
                        onValueChange = { selectedSalaryRange = it },
                        onValueChangeFinished = {
                            minSalary = selectedSalaryRange.start
                            maxSalary = selectedSalaryRange.endInclusive
                            jobViewModel.setMinSalary(minSalary.toInt().toString())
                            jobViewModel.setMaxSalary(maxSalary.toInt().toString())
                        }
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Min:", color = Color.Black)
                        OutlinedTextField(
                            value = minSalary.toInt().toString(),
                            onValueChange = {
                                minSalary = it.toFloatOrNull() ?: 0f
                            },
                            textStyle = TextStyle(color = Color.Black)
                        )
                        Text("Max:", color = Color.Black)
                        OutlinedTextField(
                            value = maxSalary.toInt().toString(),
                            onValueChange = {
                                maxSalary = it.toFloatOrNull() ?: 999999f
                            },
                            textStyle = TextStyle(color = Color.Black)
                        )
                    }
                }
                Sidebar(onSelect = { selectedJobTypes ->
                    jobViewModel.setJobType(selectedJobTypes.joinToString(","))
                })

            }
        },
        confirmButton = {
            Button(onClick = {
                jobViewModel.setJobTitle(if (jobTitle.isBlank()) null else jobTitle)
                jobViewModel.setLocation(if (location.isBlank()) null else location)
                jobViewModel.setExperience(if (experience.isBlank()) null else experience)
                onDismiss()
            }) {
                Text("Apply", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel", color = Color.Black)
            }
        },
        containerColor = Color.White,
        titleContentColor = Color.Black,
        textContentColor = Color.Black
    )
}

@Composable
fun Dropdown(
    name: String,
    options: List<String>,
    onSelect: (String, String) -> Unit,
    textStyle: TextStyle
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("") }

    Column {
        Text(text = name, color = textStyle.color)
        OutlinedButton(onClick = { expanded = true }) {
            Text(text = selectedOptionText.ifEmpty { "Select $name" }, color = textStyle.color)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option, color = textStyle.color) },
                    onClick = {
                        selectedOptionText = option
                        onSelect(name, option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun Sidebar(
    onSelect: (List<String>) -> Unit
) {
    val jobTypes = remember { listOf("Full-time", "Part-time", "Contract", "Internship") } // Replace with your actual job types
    val checkedState = remember { mutableStateMapOf<String, Boolean>().apply {
        jobTypes.forEach { this[it] = false } // Initialize all to false
    } }

    Column(
        modifier = Modifier
            .width(150.dp)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally // Center content horizontally
    ) {
        Text(text = "Job Types", color = Color.Black, textAlign = TextAlign.Center)

        jobTypes.forEach { jobType ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = checkedState[jobType] ?: false,
                    onCheckedChange = { isChecked ->
                        checkedState[jobType] = isChecked
                        onSelect(checkedState.filterValues { it }.keys.toList()) // Send the list of checked job types
                    }
                )
                Text(jobType, color = Color.Black)
            }
        }
    }
}