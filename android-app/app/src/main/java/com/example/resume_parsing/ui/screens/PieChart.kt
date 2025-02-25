package com.example.resume_parsing.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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