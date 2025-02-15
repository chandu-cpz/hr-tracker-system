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
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.resume_parsing.App
import com.example.resume_parsing.network.RetrofitClient
import com.example.resume_parsing.network.UserResponse
import com.example.resume_parsing.utils.FileUtils
import com.example.resume_parsing.utils.PreferencesHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.net.URL

@Composable
fun InfoItem(label: String, value: String) {
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Text("$label: ", fontSize = 16.sp, color = Color.Black)
        Text(value.ifEmpty { "-" }, fontSize = 16.sp, color = Color.Black)
    }
}

@Composable
fun ShowFileViewer(context: Context, pdfUrl: String){
    Text(
        text = "Error loading resume. Tap to View as PDF",
        textAlign = TextAlign.Center,
        modifier = Modifier.clickable {
            // Launch the intent here, outside of the composable scope
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(Uri.parse(pdfUrl), "application/pdf")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            val new_intent = Intent.createChooser(intent, "Open File")
            startActivity(context, new_intent, null)
        }
    )

}

//@Composable //Remove composable here
//fun FileViewer(context: Context, pdfUrl: String) { //FileViewer is no longer needed
//    val intent = Intent(Intent.ACTION_VIEW)
//    intent.setDataAndType(Uri.parse(pdfUrl), "application/pdf")
//    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//    val new_intent = Intent.createChooser(intent, "Open File")
//    startActivity(context, new_intent, null) //Cannot be called inside composable function
//}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun ProfileScreen(navController: NavHostController) {
    val context = LocalContext.current

    // Get data from SharedPreferences once and remember it
    val initialUserData = remember { PreferencesHelper.getUserData(context) }
    val userData = remember { mutableStateOf(initialUserData) } // MutableState for potential updates

    val userRole = userData.value?.role ?: "USER"
    val resumeUrl = userData.value?.resume // Retrieve resumeUrl from shared preferences

    var showEditDialog by remember { mutableStateOf(false) }
    var isResumeExpanded by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    // MutableState to hold the converted Bitmap (or null if there's an error)
    val pdfBitmapState = remember { mutableStateOf<Bitmap?>(null) }

    // Save UserData to SharedPreferences
    fun saveUserData(user: UserResponse) {
        PreferencesHelper.saveUserData(context, user)
        userData.value = user // Update the MutableState
    }

    // Function to convert PDF to Bitmap
    suspend fun convertPdfToBitmap(url: String?): Bitmap? {
        if (url.isNullOrBlank()) {
            return null
        }

        return withContext(Dispatchers.IO) {
            try {
                // Download the PDF from the URL
                val pdfUrl = URL(url)
                val connection = pdfUrl.openConnection()
                connection.connect()
                val inputStream = connection.getInputStream()

                // Create a temporary file
                val tempFile = File.createTempFile("resume", ".pdf", context.cacheDir)
                val outputStream = FileOutputStream(tempFile)

                inputStream.copyTo(outputStream)
                inputStream.close()
                outputStream.close()

                val parcelFileDescriptor: ParcelFileDescriptor? =
                    ParcelFileDescriptor.open(tempFile, ParcelFileDescriptor.MODE_READ_ONLY)
                val pdfRenderer = parcelFileDescriptor?.let { PdfRenderer(it) }

                if (pdfRenderer != null) {
                    val pageCount = pdfRenderer.pageCount
                    Log.d("PDFPageCount", "Number of pages in PDF: $pageCount")
                    if (pageCount > 0) {
                        val page = pdfRenderer.openPage(0)
                        val bitmap: Bitmap = Bitmap.createBitmap(
                            page?.width ?: 1,
                            page?.height ?: 1,
                            Bitmap.Config.ARGB_8888
                        ) // Add default values to prevent NPE
                        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                        page.close()
                        pdfRenderer.close()
                        parcelFileDescriptor.close()
                        return@withContext bitmap
                    } else {
                        pdfRenderer.close()
                        parcelFileDescriptor.close()
                        Log.e("PdfConverter", "Could not load the first PDF page")
                        return@withContext null
                    }
                } else {
                    Log.e("PdfConverter", "Could not create PdfRenderer")
                    return@withContext null
                }

            } catch (e: Exception) {
                Log.e("PdfConverter", "Error converting PDF to Bitmap", e)
                return@withContext null
            }
        }
    }

    // Load PDF as Image at Composition
    LaunchedEffect(resumeUrl) {
        if (!resumeUrl.isNullOrBlank()) {
            isLoading = true
            coroutineScope.launch {
                val bitmap = convertPdfToBitmap(resumeUrl)
                withContext(Dispatchers.Main) {
                    pdfBitmapState.value = bitmap
                    isLoading = false
                }
            }
        }
    }

    // Activity Result Launcher for PDF picking
    val pdfPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let { pdfUri ->
                coroutineScope.launch {
                    isLoading = true
                    try {
                        val file = FileUtils.uriToFile(pdfUri, context)
                        if (file != null) {
                            val requestFile = file.asRequestBody("application/pdf".toMediaTypeOrNull())
                            val body = MultipartBody.Part.createFormData("resume", file.name, requestFile)
                            val signedPreset = RetrofitClient.apiService.getSignedUploadPreset("resume").signedPreset
                            val response = RetrofitClient.apiService.uploadFile(
                                "your_cloudinary_cloud_name", // Replace with your cloud name
                                file = body,
                                folder = "resume",
                                timestamp = System.currentTimeMillis(),
                                apiKey = "your_cloudinary_api_key", // Replace with your API Key
                                uploadPreset = signedPreset
                            )
                            if (response.secure_url != null) {
                                // Update the User Data and Save
                                val updatedUser = userData.value?.copy(resume = response.secure_url)
                                if (updatedUser != null) {
                                    saveUserData(updatedUser)

                                    val newBitmap = convertPdfToBitmap(response.secure_url)
                                    withContext(Dispatchers.Main) {
                                        pdfBitmapState.value = newBitmap
                                    }
                                }
                                Toast.makeText(context, "Resume Uploaded Successful", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Upload Failed", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "Could not create file from URI", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.e("ProfileScreen", "Error uploading PDF", e)
                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    } finally {
                        isLoading = false
                    }
                }
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Making Profile Screen Scrollable
            .background(Color(0xFF2C3E50)),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFE5C2)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp) // Adding padding to the entire column
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                ) {
                    val imageUrl = userData.value?.profileImage ?: ""
                    Image(
                        painter = rememberAsyncImagePainter(
                            if (imageUrl.isNotEmpty()) imageUrl
                            else "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS2IYhSn8Y9S9_HF3tVaYOepJBcrYcd809pBA&s"
                        ),
                        contentDescription = "Profile Picture",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(userData.value?.fullName ?: "Unknown", fontSize = 22.sp, color = Color(0xFFE65100))
                Text("\uD83D\uDCE7 ${userData.value?.email ?: "No Email"}", fontSize = 14.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { showEditDialog = true },
                    colors = ButtonDefaults.buttonColors(Color(0xFFFF7043))
                ) {
                    Text("Edit Profile", color = Color.White)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        RetrofitClient.apiService.logout().enqueue(object : Callback<ResponseBody> {
                            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                                if (response.isSuccessful) {
                                    PreferencesHelper.clearPreferences(App.context)
                                    Toast.makeText(App.context, "Logged out successfully", Toast.LENGTH_SHORT).show()
                                    navController.navigate("login") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                } else {
                                    Toast.makeText(App.context, "Error logging out", Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                Toast.makeText(App.context, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                            }
                        })
                    },
                    colors = ButtonDefaults.buttonColors(Color.Red)
                ) {
                    Text("Log Out", color = Color.White)
                }
            }
        }

        // Resume Section (moved outside Card)
        if (userRole == "USER") {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                if (resumeUrl.isNullOrEmpty()) {
                    Text(text = "Upload Your Resume", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(Color.Gray.copy(alpha = 0.2f))
                            .clickable { pdfPickerLauncher.launch("application/pdf") },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Filled.AttachFile,
                            contentDescription = "Upload",
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                    }

                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.padding(top = 8.dp))
                    }
                } else {
                    //Show file from Image URL or file picker
                    Text(text = "Your Resume", fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(if (isResumeExpanded) 400.dp else 200.dp) // increased box size
                            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {

                        if (isLoading) {
                            CircularProgressIndicator()
                        } else if (pdfBitmapState.value != null) {
                            // Display the PDF as an Image
                            Image(
                                bitmap = pdfBitmapState.value!!.asImageBitmap(),
                                contentDescription = "Resume Image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Fit
                            )
                        } else {
                            // Composable FileViewer Invocation
                            ShowFileViewer(context = context,pdfUrl = resumeUrl )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(onClick = { isResumeExpanded = !isResumeExpanded }) {
                            Text(if (isResumeExpanded) "Show Less" else "View Full Resume")
                        }

                        Button(
                            onClick = { pdfPickerLauncher.launch("application/pdf") },
                            colors = ButtonDefaults.buttonColors(Color(0xFFFF7043))
                        ) {
                            Text("Change Resume", color = Color.White)
                        }
                    }
                }
            }
        }

        // Info/Experience/Skills Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(Color(0xFFFFE5C2))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Info", fontSize = 18.sp, color = Color(0xFFE65100))
                        Spacer(modifier = Modifier.height(8.dp))
                        InfoItem(label = "Gender", value = userData.value?.gender ?: "")
                        InfoItem(label = "Location", value = userData.value?.address ?: "")
                        InfoItem(label = "Education", value = userData.value?.education ?: "")
                    }

                    Column {
                        Text("Experience", fontSize = 18.sp, color = Color(0xFFE65100))
                        Spacer(modifier = Modifier.height(8.dp))
                        InfoItem(label = "Years", value = userData.value?.experience ?: "")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Skills", fontSize = 18.sp, color = Color(0xFFE65100))
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = userData.value?.skills?.takeIf { it.isNotEmpty() }?.joinToString(", ") ?: "-",
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        }
    }

    if (showEditDialog) {
        val userId = userData.value?._id ?: ""

        EditProfileDialog(
            user = userData.value,
            userId = userId,
            onDismiss = { showEditDialog = false },
            onSave = { updatedUserRequest -> // Receive the updated user data
                coroutineScope.launch {
                    try {
                        val response = RetrofitClient.apiService.updateUser(updatedUserRequest)
                        if (response.isSuccessful) {
                            val updatedUser = response.body()
                            if (updatedUser != null) {
                                saveUserData(updatedUser) // Save to SharedPreferences
                                Toast.makeText(context, "Profile Updated Successfully", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "Failed to Update Profile${response.body()}", Toast.LENGTH_LONG).show()
                            Log.d("Error api","Failed to Update Profile ${response}")
                        }
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    } finally {
                        showEditDialog = false // Dismiss dialog after save attempt
                    }
                }
            }
        )
    }
}