//
//
//package com.example.resume_parsing.ui.screens
//
//import ApplicantsViewModel
//import android.content.ContentValues.TAG
//import android.content.Context
//import android.content.Intent
//import android.graphics.Bitmap
//import android.graphics.pdf.PdfRenderer
//import android.net.Uri
//import android.os.Build
//import android.os.ParcelFileDescriptor
//import android.util.Log
//import android.widget.Toast
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.annotation.RequiresApi
//import androidx.compose.foundation.*
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.filled.AttachFile
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.asImageBitmap
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.core.content.ContextCompat.startActivity
//import androidx.navigation.NavHostController
//import coil.compose.rememberAsyncImagePainter
//import com.example.resume_parsing.App
//import com.example.resume_parsing.network.RetrofitClient
//import com.example.resume_parsing.network.UserResponse
//import com.example.resume_parsing.utils.FileUtils
//import com.example.resume_parsing.utils.PreferencesHelper
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//import okhttp3.MediaType.Companion.toMediaTypeOrNull
//import okhttp3.MultipartBody
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.animation.*
//import androidx.compose.animation.core.*
//import androidx.compose.ui.draw.shadow
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.ui.res.painterResource
//import androidx.core.content.ContextCompat
//
//import androidx.compose.runtime.LaunchedEffect
//
//import androidx.compose.ui.Alignment.Companion.CenterHorizontally
//import androidx.compose.ui.tooling.preview.Preview
//import java.io.IOException
//import java.lang.Exception
//import java.net.HttpURLConnection
//import java.net.MalformedURLException
//import java.net.URLConnection
//import javax.net.ssl.HttpsURLConnection
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Email
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material.icons.filled.Phone
//import androidx.compose.material.icons.filled.School
//import androidx.compose.material.icons.filled.Work
//import androidx.compose.material.icons.filled.LocationOn
//import androidx.compose.material3.Icon
//import androidx.compose.ui.graphics.ImageBitmap
//import androidx.compose.ui.graphics.painter.Painter
//import androidx.compose.ui.text.font.FontWeight
//import androidx.lifecycle.viewmodel.compose.viewModel
//import coil.compose.AsyncImage
//import com.example.resume_parsing.App.Companion.context
//import com.example.resume_parsing.viewmodel.ApplicationDetailsViewModel
//import java.io.File
//import java.io.FileOutputStream
//import java.net.URL
//
//@Composable
//fun ApplicationDetailsScreen(applicationId: String, navController: NavHostController) {
//    val viewModel: ApplicationDetailsViewModel = viewModel()
//    viewModel.loadApplicationDetails(applicationId)
//    val application = viewModel.application.value
//
//    val errorMessage = viewModel.errorMessage.value
//    val accepted = viewModel.accepted.value
//    val rejected = viewModel.rejected.value
//    val showResume = rememberSaveable { mutableStateOf(false) }
//
//    //Get current context
//    val context = LocalContext.current
//    val pdfBitmapState = remember { mutableStateOf<Bitmap?>(null) }
//    val resumeLoading = remember { mutableStateOf(false) }
//    val resumeError = remember { mutableStateOf<String?>(null) }
//    // State to hold the converted image
//
//
//    LaunchedEffect(application?.resume) {
//        if (application?.resume != null) {
//            resumeLoading.value = true
//            resumeError.value = null // Clear any previous errors
//            try {
//                val resumeLink = application.resume
//                if (resumeLink != null) {
//                    val bitmap = convertPdfToBitmap(resumeLink)
//                    pdfBitmapState.value = bitmap
//                }
//            } catch (e: Exception) {
//                // Handle exceptions (e.g., network issues, PDF rendering errors)
//                Log.e(TAG, "Error converting PDF to image: ${e.message}")
//                resumeError.value = "Failed to load resume: ${e.message}"
//                pdfBitmapState.value = null // Clear the bitmap
//            } finally {
//                resumeLoading.value = false
//            }
//        }
//    }
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFF2C3E50))
//            .verticalScroll(rememberScrollState())  // Enable scrolling
//            .padding(16.dp)
//    ) {
//        if (errorMessage != null) {
//            Text(
//                text = "Error: $errorMessage",
//                color = Color.Red,
//                modifier = Modifier.padding(8.dp)
//            )
//        } else if (application == null) {
//            Box(
//                contentAlignment = Alignment.Center,
//                modifier = Modifier.fillMaxSize()
//            ) {
//                CircularProgressIndicator(color = Color.White)
//            } // Centered Loading
//        } else {
//            // Applicant Profile
//            application?.let { app ->
//
//                // Remove card for the whole content
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp)
//                ) {
//
//                    Text(
//                        text = "Applicant Details",
//                        fontSize = 24.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color.White,
//                        textAlign = TextAlign.Center,
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                    Spacer(modifier = Modifier.height(8.dp))
//                    // Profile Image
//
//                    AsyncImage(
//                        model = app.appliedBy.profileImage,
//                        contentDescription = "Profile Image",
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier
//                            .size(100.dp)
//                            .clip(CircleShape)
//                            .align(Alignment.CenterHorizontally)
//                    )
//
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    DetailRow(
//                        icon = Icons.Filled.Person,
//                        label = "Name",
//                        value = app.appliedBy.fullName
//                    )
//                    DetailRow(
//                        icon = Icons.Filled.Email,
//                        label = "Email",
//                        value = app.appliedBy.email
//                    )
//
//                    DetailRow(
//                        icon = Icons.Filled.Phone,
//                        label = "Phone",
//                        value = app.appliedBy.phoneNumber ?: "N/A"
//                    )
//
//                    DetailRow(
//                        icon = Icons.Filled.School,
//                        label = "Education",
//                        value = app.appliedBy.education
//                    )
//                    DetailRow(
//                        icon = Icons.Filled.Work,
//                        label = "Experience",
//                        value = app.appliedBy.experience ?: "N/A"
//                    )
//                    DetailRow(
//                        icon = Icons.Filled.LocationOn,
//                        label = "Address",
//                        value = app.appliedBy.address ?: "N/A"
//                    )
//                    SkillsRow(skills = app.appliedBy.skills)
//                    Spacer(modifier = Modifier.height(16.dp))
//                    // View Resume Button
//                    Button(
//                        onClick = {
//                            showResume.value = !showResume.value
//                        },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(top = 0.dp),
//                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5DADE2)),
//                        shape = RoundedCornerShape(8.dp)
//                    ) {
//                        Text(text = if (showResume.value) "Hide Resume" else "View Resume", color = Color.White)
//                    }
//
//                    // Display Resume Below
//                    AnimatedVisibility(
//                        visible = showResume.value,
//                        enter = fadeIn(animationSpec = tween(durationMillis = 500)),
//                        exit = fadeOut(animationSpec = tween(durationMillis = 500))
//                    ) {
//                        Column(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(400.dp) // Adjust the height as needed
//                                .padding(top = 8.dp)
//                                .clip(RoundedCornerShape(8.dp))
//                                .shadow(4.dp, RoundedCornerShape(8.dp)) // Add shadow
//                                .background(Color(0xFF4E6984)) //Optional Background
//                                .padding(8.dp),
//                            horizontalAlignment = CenterHorizontally
//                        ) {
//                            if (resumeLoading.value) {
//                                CircularProgressIndicator(color = Color.White)
//                            } else if (resumeError.value != null) {
//                                Text(
//                                    text = "Error loading resume: ${resumeError.value}",
//                                    color = Color.Red
//                                )
//                            } else if (pdfBitmapState.value != null) {
//                                Image(
//                                    bitmap = pdfBitmapState.value!!.asImageBitmap(),
//                                    contentDescription = "Resume",
//                                    modifier = Modifier.fillMaxWidth(),
//                                    contentScale = ContentScale.Fit
//                                )
//                            } else {
//                                Text("No resume available.", color = Color.Gray)
//                            }
//                        }
//                    }
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    // Remove card for the whole content
//
//                    // Action Buttons with Status Display
//                    Row(
//                        horizontalArrangement = Arrangement.SpaceAround,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(top = 16.dp)
//                    ) {
//                        if (app.accepted == "PENDING") {
//                            ActionButton(
//                                text = "Accept",
//                                onClick = {
//                                    viewModel.acceptApplication(app.id)
//                                    Toast.makeText(context, "Application Accepted", Toast.LENGTH_SHORT).show()
//                                },
//                                enabled = !accepted && !rejected
//                            )
//                            ActionButton(
//                                text = "Reject",
//                                onClick = {
//                                    viewModel.rejectApplication(app.id)
//                                },
//                                enabled = !accepted && !rejected
//                            )
//                        } else if (app.accepted == "ACCEPTED") {
//
//                            ActionButton(
//                                text = "Reject",
//                                onClick = {
//                                    viewModel.rejectApplication(app.id)
//                                },
//                                enabled = !accepted && !rejected
//                            )
//                        }
//                    }
//
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    // Status Message
//                    val statusText = when {
//                        app.accepted == "ACCEPTED" -> "Application Accepted"
//                        app.accepted == "REJECTED" -> "Application Rejected"
//                        app.accepted == "PENDING" -> "Application Pending"
//                        else -> "Application Pending"
//                    }
//                    if (statusText.isNotEmpty()) {
//                        Text(
//                            text = statusText,
//                            color = when (app.accepted) {
//                                "ACCEPTED" -> Color.Green
//                                "REJECTED" -> Color.Red
//                                else -> Color.Yellow
//                            },
//                            textAlign = TextAlign.Center,
//                            modifier = Modifier.fillMaxWidth()
//                        )
//                    }
//
//                }
//            }
//        }
//    }
//}
//
//suspend fun convertPdfToBitmap(url: String?): Bitmap? {
//    if (url.isNullOrBlank()) {
//        return null
//    }
//
//    return withContext(Dispatchers.IO) {
//        try {
//            // Download the PDF from the URL
//            val pdfUrl = URL(url)
//            val connection = pdfUrl.openConnection()
//            connection.connect()
//            val inputStream = connection.getInputStream()
//
//            // Create a temporary file
//            val tempFile = File.createTempFile("resume", ".pdf", context.cacheDir)
//            FileOutputStream(tempFile).use { outputStream ->
//                inputStream.use { input ->
//                    input.copyTo(outputStream)
//                }
//            }
//
//            val parcelFileDescriptor: ParcelFileDescriptor? =
//                ParcelFileDescriptor.open(tempFile, ParcelFileDescriptor.MODE_READ_ONLY)
//            val pdfRenderer = parcelFileDescriptor?.let { PdfRenderer(it) }
//
//            if (pdfRenderer != null) {
//                val pageCount = pdfRenderer.pageCount
//                Log.d("PDFPageCount", "Number of pages in PDF: $pageCount")
//                if (pageCount > 0) {
//                    val page = pdfRenderer.openPage(0)
//                    val bitmap: Bitmap = Bitmap.createBitmap(
//                        page?.width ?: 1,
//                        page?.height ?: 1,
//                        Bitmap.Config.ARGB_8888
//                    ) // Add default values to prevent NPE
//                    page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
//                    page.close()
//                    pdfRenderer.close()
//                    parcelFileDescriptor.close()
//                    return@withContext bitmap
//                } else {
//                    pdfRenderer.close()
//                    parcelFileDescriptor.close()
//                    Log.e("PdfConverter", "Could not load the first PDF page")
//                    return@withContext null
//                }
//            } else {
//                Log.e("PdfConverter", "Could not create PdfRenderer")
//                return@withContext null
//            }
//
//        } catch (e: Exception) {
//            Log.e("PdfConverter", "Error converting PDF to Bitmap", e)
//            return@withContext null
//        }
//    }
//}
//
//@Composable
//fun DetailRow(icon: ImageVector, label: String, value: String) {
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier.padding(vertical = 4.dp)
//    ) {
//        Icon(
//            imageVector = icon,
//            contentDescription = label,
//            tint = Color.White, // Adjust icon color
//            modifier = Modifier.size(20.dp)
//        )
//        Spacer(modifier = Modifier.width(8.dp))
//        Text(text = "$label: ", fontWeight = FontWeight.Bold, color = Color.White)
//        Text(text = value, color = Color.White, overflow = TextOverflow.Ellipsis, maxLines = 1)
//    }
//}
//
//@OptIn(ExperimentalLayoutApi::class)
//@Composable
//fun SkillsRow(skills: List<String>) {
//    Column(
//        modifier = Modifier.padding(vertical = 4.dp)
//    ) {
//        Text(text = "Skills:", fontWeight = FontWeight.Bold, color = Color.White)
//        FlowRow(
//            horizontalArrangement = Arrangement.spacedBy(4.dp),
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            skills.forEach { skill ->
//                Surface(
//                    color = Color(0xFF4E6984), // A lighter shade for skills
//                    shape = MaterialTheme.shapes.small,
//                    modifier = Modifier.padding(vertical = 2.dp)
//                ) {
//                    Text(
//                        text = skill,
//                        color = Color.White,
//                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun ActionButton(text: String, onClick: () -> Unit, enabled: Boolean) {
//    Button(
//        onClick = onClick,
//        enabled = enabled,
//        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5DADE2)) // Customize button color
//    ) {
//        Text(text = text, color = Color.White)
//    }
//}

package com.example.resume_parsing.ui.screens

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Build
import android.os.ParcelFileDescriptor
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.resume_parsing.App
import com.example.resume_parsing.viewmodel.ApplicationDetailsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL

@Composable
fun ApplicationDetailsScreen(applicationId: String, navController: NavHostController) {
    val viewModel: ApplicationDetailsViewModel = viewModel()
    viewModel.loadApplicationDetails(applicationId)
    val application = viewModel.application.value

    val errorMessage = viewModel.errorMessage.value
    val accepted = viewModel.accepted.value
    val rejected = viewModel.rejected.value
    val showResume = remember { mutableStateOf(false) }

    val context = LocalContext.current
    val pdfBitmapState = remember { mutableStateOf<Bitmap?>(null) }
    val resumeLoading = remember { mutableStateOf(false) }
    val resumeError = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(application?.resume) {
        if (application?.resume != null) {
            resumeLoading.value = true
            resumeError.value = null
            try {
                val resumeLink = application.resume
                if (resumeLink != null) {
                    val bitmap = convertPdfToBitmap(resumeLink)
                    pdfBitmapState.value = bitmap
                }
            } catch (e: Exception) {
                Log.e("PDF_ERROR", "Error converting PDF to image: ${e.message}")
                resumeError.value = "Failed to load resume: ${e.message}"
                pdfBitmapState.value = null
            } finally {
                resumeLoading.value = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2C3E50))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        if (errorMessage != null) {
            Text(
                text = "Error: $errorMessage",
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        } else if (application == null) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        } else {
            application?.let { app ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Applicant Details",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    AsyncImage(
                        model = app.appliedBy.profileImage,
                        contentDescription = "Profile Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    DetailRow(
                        icon = Icons.Filled.Person,
                        label = "Name",
                        value = app.appliedBy.fullName
                    )
                    DetailRow(
                        icon = Icons.Filled.Email,
                        label = "Email",
                        value = app.appliedBy.email
                    )
                    DetailRow(
                        icon = Icons.Filled.Phone,
                        label = "Phone",
                        value = app.appliedBy.phoneNumber ?: "N/A"
                    )
                    DetailRow(
                        icon = Icons.Filled.School,
                        label = "Education",
                        value = app.appliedBy.education
                    )
                    DetailRow(
                        icon = Icons.Filled.Work,
                        label = "Experience",
                        value = app.appliedBy.experience ?: "N/A"
                    )
                    DetailRow(
                        icon = Icons.Filled.LocationOn,
                        label = "Address",
                        value = app.appliedBy.address ?: "N/A"
                    )

                    SkillsRow(skills = app.appliedBy.skills)

                    Spacer(modifier = Modifier.height(24.dp))

                    // View Resume Button
                    Button(
                        onClick = { showResume.value = !showResume.value },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF5DADE2)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = if (showResume.value) "Hide Resume" else "View Resume",
                            color = Color.White
                        )
                    }

                    // Resume Preview Section
                    AnimatedVisibility(
                        visible = showResume.value,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp)
                                .padding(vertical = 8.dp)
                                .background(Color(0xFF4E6984), RoundedCornerShape(8.dp))
                                .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                        ) {
                            when {
                                resumeLoading.value -> {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        CircularProgressIndicator(color = Color.White)
                                    }
                                }
                                resumeError.value != null -> {
                                    Text(
                                        text = resumeError.value!!,
                                        color = Color.Red,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                                pdfBitmapState.value != null -> {
                                    Image(
                                        bitmap = pdfBitmapState.value!!.asImageBitmap(),
                                        contentDescription = "Resume",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Fit
                                    )
                                }
                                else -> {
                                    Text(
                                        "No resume available",
                                        color = Color.Gray,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Action Buttons
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        when (app.accepted) {
                            "PENDING" -> {
                                ActionButton(
                                    text = "Accept",
                                    onClick = {
                                        viewModel.acceptApplication(app.id)
                                        Toast.makeText(context, "Application Accepted", Toast.LENGTH_SHORT).show()
                                    },
                                    enabled = true
                                )
                                ActionButton(
                                    text = "Reject",
                                    onClick = {
                                        viewModel.rejectApplication(app.id)
                                        Toast.makeText(context, "Application Rejected", Toast.LENGTH_SHORT).show()
                                    },
                                    enabled = true
                                )
                            }
                            "ACCEPTED" -> {
                                ActionButton(
                                    text = "Reject",
                                    onClick = {
                                        viewModel.rejectApplication(app.id)
                                        Toast.makeText(context, "Application Rejected", Toast.LENGTH_SHORT).show()
                                    },
                                    enabled = true
                                )
                            }
                            "REJECTED" -> {
                                ActionButton(
                                    text = "Accept",
                                    onClick = {
                                        viewModel.acceptApplication(app.id)
                                        Toast.makeText(context, "Application Accepted", Toast.LENGTH_SHORT).show()
                                    },
                                    enabled = true
                                )
                            }
                        }
                    }

                    // Status Indicator
                    Text(
                        text = when (app.accepted) {
                            "ACCEPTED" -> "Status: Accepted"
                            "REJECTED" -> "Status: Rejected"
                            else -> "Status: Pending"
                        },
                        color = when (app.accepted) {
                            "ACCEPTED" -> Color.Green
                            "REJECTED" -> Color.Red
                            else -> Color.Yellow
                        },
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun ActionButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .width(120.dp)
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = when (text) {
                "Accept" -> Color(0xFF2ECC71)
                "Reject" -> Color(0xFFE74C3C)
                else -> Color(0xFF5DADE2)
            },
            disabledContainerColor = Color.Gray
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text = text, color = Color.White)
    }
}

@Composable
fun DetailRow(icon: ImageVector, label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp
            )
            Text(
                text = value,
                color = Color.White,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SkillsRow(skills: List<String>) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = "Skills:",
            fontWeight = FontWeight.Bold,
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 14.sp
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(top = 8.dp)
        ) {
            skills.forEach { skill ->
                Surface(
                    color = Color(0xFF4E6984),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .clickable { }
                        .shadow(2.dp, RoundedCornerShape(16.dp))
                ) {
                    Text(
                        text = skill,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

suspend fun convertPdfToBitmap(url: String?): Bitmap? {
    if (url.isNullOrBlank()) return null

    return withContext(Dispatchers.IO) {
        try {
            val pdfUrl = URL(url)
            val connection = pdfUrl.openConnection()
            connection.connect()
            val inputStream = connection.getInputStream()

            val tempFile = File.createTempFile("resume", ".pdf", App.context.cacheDir)
            FileOutputStream(tempFile).use { outputStream ->
                inputStream.use { input ->
                    input.copyTo(outputStream)
                }
            }

            val parcelFileDescriptor = ParcelFileDescriptor.open(tempFile, ParcelFileDescriptor.MODE_READ_ONLY)
            val pdfRenderer = PdfRenderer(parcelFileDescriptor)

            if (pdfRenderer.pageCount > 0) {
                val page = pdfRenderer.openPage(0)
                val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                page.close()
                pdfRenderer.close()
                parcelFileDescriptor.close()
                bitmap
            } else {
                pdfRenderer.close()
                parcelFileDescriptor.close()
                null
            }
        } catch (e: Exception) {
            Log.e("PDF_CONVERSION", "Error converting PDF to Bitmap", e)
            null
        }
    }
}