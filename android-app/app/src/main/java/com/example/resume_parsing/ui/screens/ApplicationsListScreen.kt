package com.example.resume_parsing.ui.screens

import ApplicantsViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.resume_parsing.network.Applicant
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.draw.clip

@Composable
fun ApplicationsListScreen(navController: NavHostController) {
    val viewModel: ApplicantsViewModel = viewModel()
    val applicants = viewModel.applicants
    val errorMessage = viewModel.errorMessage

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2C3E50)), // Dark blue background
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Applicants",
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            color = Color.White, // White text for contrast
            modifier = Modifier.padding(16.dp)
        )

        if (errorMessage != null) {
            Text(
                text = "Error: $errorMessage",
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        } else if (applicants.isEmpty()) {
            CircularProgressIndicator(color = Color.White) // White loading indicator
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(applicants) { applicant ->
                    ApplicantCard(applicant = applicant, navController = navController)
                }
            }
        }
    }
}

@Composable
fun ApplicantCard(applicant: Applicant, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("applicationDetails/${applicant.id}") // Navigate with ID
            }
            .padding(4.dp)
            .clip(RectangleShape), // Clip the card for rounded corners

        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF34495E), // Darker blue for the card background
            contentColor = Color.White // White text
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Name: ${applicant.appliedBy.fullName}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold // Bold the name
            )
            Spacer(modifier = Modifier.height(4.dp)) // Add some space between the name and email
            Text(text = "Email: ${applicant.appliedBy.email}", fontSize = 14.sp)
        }
    }
}