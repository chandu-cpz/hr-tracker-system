package com.example.resume_parsing.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Define chart colors for consistency (can be moved to a theme file later)
object ChartColors {
    val Accepted = Color(0xFF4CAF50) // Green
    val Pending = Color(0xFF2196F3) // Blue
    val Rejected = Color(0xFFF44336) // Red
    val Male = Color(0xFF2196F3)    // Blue
    val Female = Color(0xFFE91E63)  // Pink/Red
    val Others = Color(0xFFFFC107)  // Amber/Yellow
    val NoData = Color.Gray
}

@Composable
fun PieChart(
    // Use default arguments for clarity
    accepted: Float = 0f,
    pending: Float = 0f,
    rejected: Float = 0f,
    male: Float = 0f,
    female: Float = 0f,
    others: Float = 0f,
    chartTitle: String
) {
    // Determine chart type (status vs gender)
    val isStatusChart = male == 0f && female == 0f && others == 0f // More robust check
    val isGenderChart = !isStatusChart

    val total = when {
        isStatusChart -> accepted + pending + rejected
        isGenderChart -> male + female + others
        else -> 0f // Should not happen with current logic, but good practice
    }

    val allZero = total == 0f

    // Calculate percentages safely
    val acceptedPercentage = if (isStatusChart && total > 0f) accepted / total else 0f
    val pendingPercentage = if (isStatusChart && total > 0f) pending / total else 0f
    val rejectedPercentage = if (isStatusChart && total > 0f) rejected / total else 0f

    val malePercentage = if (isGenderChart && total > 0f) male / total else 0f
    val femalePercentage = if (isGenderChart && total > 0f) female / total else 0f
    val othersPercentage = if (isGenderChart && total > 0f) others / total else 0f

    Card( // Wrap the chart and legend in a Card
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant // Use a theme surface color
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp), // Padding inside the card
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Chart Title
            Text(
                text = chartTitle,
                style = MaterialTheme.typography.titleMedium, // Use theme typography
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant // Text color on surfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp)) // Space between title and chart/legend

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly // Space out chart and legend
            ) {
                // Simplified "Pie" Chart (Segmented Bar Representation)
                // Using weights for better space distribution
                Column(
                    modifier = Modifier.weight(1f), // Take available space
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Distribution",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .height(20.dp) // Make the bar thicker
                            .fillMaxWidth(0.8f) // Control width of the bar itself
                            .clip(MaterialTheme.shapes.small) // Clip the whole bar
                    ) {
                        if (allZero) {
                            // Display a single gray bar if all values are zero
                            SegmentedBarSection(percentage = 1f, color = ChartColors.NoData)
                        } else {
                            if (isStatusChart) {
                                SegmentedBarSection(percentage = acceptedPercentage, color = ChartColors.Accepted)
                                SegmentedBarSection(percentage = pendingPercentage, color = ChartColors.Pending)
                                SegmentedBarSection(percentage = rejectedPercentage, color = ChartColors.Rejected)
                            } else { // isGenderChart
                                SegmentedBarSection(percentage = malePercentage, color = ChartColors.Male)
                                SegmentedBarSection(percentage = femalePercentage, color = ChartColors.Female)
                                SegmentedBarSection(percentage = othersPercentage, color = ChartColors.Others)
                            }
                        }
                    }
                }

                // Legend
                Column(modifier = Modifier.weight(1f)) { // Take available space
                    Text(
                        "Legend",
                         style = MaterialTheme.typography.labelSmall,
                         color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    if (allZero) {
                        LegendItem(color = ChartColors.NoData, text = "No Data")
                    } else {
                        if (isStatusChart) {
                            LegendItem(color = ChartColors.Accepted, text = "Accepted (${(acceptedPercentage * 100).toInt()}%)")
                            LegendItem(color = ChartColors.Pending, text = "Pending (${(pendingPercentage * 100).toInt()}%)")
                            LegendItem(color = ChartColors.Rejected, text = "Rejected (${(rejectedPercentage * 100).toInt()}%)")
                        } else { // isGenderChart
                            LegendItem(color = ChartColors.Male, text = "Male (${(malePercentage * 100).toInt()}%)")
                            LegendItem(color = ChartColors.Female, text = "Female (${(femalePercentage * 100).toInt()}%)")
                            LegendItem(color = ChartColors.Others, text = "Others (${(othersPercentage * 100).toInt()}%)")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RowScope.SegmentedBarSection(percentage: Float, color: Color) {
    // Ensure percentage is not negative and finite
    val safePercentage = percentage.coerceIn(0f, 1f).takeIf { it.isFinite() } ?: 0f
    if (safePercentage > 0.001f) { // Only draw if the section is visibly large enough
        Box(
            modifier = Modifier
                .fillMaxHeight() // Fill height of parent Row
                .weight(safePercentage) // Use weight based on percentage
                .background(color)
        )
    }
}

@Composable
fun LegendItem(color: Color, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp) // Add vertical padding between items
    ) {
        Box(
            modifier = Modifier
                .size(12.dp) // Slightly larger box
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(8.dp)) // Increase spacing
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall, // Use theme typography
            color = MaterialTheme.colorScheme.onSurfaceVariant // Match text color
        )
    }
}
