package com.example.resume_parsing.ui.screens

import ApplicantsViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.resume_parsing.network.Applicant
import com.example.resume_parsing.viewmodel.EmployeeViewModel

@Composable
fun EmployeesListScreen(navController: NavHostController) {
    val viewModel: EmployeeViewModel = viewModel()
    val applicants = viewModel.applicants
    val errorMessage = viewModel.errorMessage

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2C3E50)), // Dark blue background
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Employee",
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
