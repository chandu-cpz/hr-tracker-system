package com.example.resume_parsing.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
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
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color // Will use direct Color literals
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.resume_parsing.App
import com.example.resume_parsing.network.RetrofitClient
import com.example.resume_parsing.network.UserResponse
import com.example.resume_parsing.utils.FileUploadUtil
import com.example.resume_parsing.utils.FileUtils
import com.example.resume_parsing.utils.PreferencesHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// --- Helper Composables (Rethemed) ---

@Composable
fun InfoItem(label: String, value: String?) {
    // This will now appear on a light background (UserInfoCard)
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium, // Medium weight for label
            color = Color(0xFF546E7A) // Direct Use: Medium Blue-Grey (on light background)
        )
        Text(
            text = value?.takeIf { it.isNotEmpty() } ?: "-",
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFF263238) // Direct Use: Dark Blue-Grey (on light background)
        )
    }
}

@Composable
fun ProfileHeader(
    userData: UserResponse?,
    onEditClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    // Use a slightly lighter shade of the main background for the header
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF37474F)), // Direct Use: Blue Grey 700
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            // Profile Image with Orange Border
            Image(
                painter = rememberAsyncImagePainter(
                    model = userData?.profileImage?.takeIf { it.isNotEmpty() }
                        ?: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS2IYhSn8Y9S9_HF3tVaYOepJBcrYcd809pBA&s" // Placeholder
                ),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(3.dp, Color(0xFFFF9800), CircleShape), // Direct Use: Orange 500 border
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Name - prominent on dark header
            Text(
                text = userData?.fullName ?: "Unknown User",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFECEFF1) // Direct Use: Blue Grey 50 (Light text)
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Email - less prominent
            Row(verticalAlignment = Alignment.CenterVertically){
                Icon(
                    Icons.Default.Email,
                    contentDescription = "Email Icon",
                    modifier = Modifier.size(16.dp),
                    tint = Color(0xFFB0BEC5) // Direct Use: Blue Grey 200 (Muted icon)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = userData?.email ?: "No Email Provided",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFB0BEC5) // Direct Use: Blue Grey 200 (Muted text)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
            ) {
                // Edit Profile Button - Primary Action (Orange)
                Button(
                    onClick = onEditClick,
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF9800) // Direct Use: Orange 500
                    )
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Edit Profile",
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                        tint = Color(0xFF212121) // Direct Use: Dark Grey (contrast on orange)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Edit Profile", color = Color(0xFF212121)) // Direct Use: Dark Grey (contrast on orange)
                }

                // Logout Button - Secondary Action (Less prominent blue)
                Button(
                    onClick = onLogoutClick,
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF81D4FA) // Direct Use: Light Blue 300
                    )
                ) {
                     Icon(
                        Icons.Default.Logout,
                        contentDescription = "Log Out",
                         modifier = Modifier.size(ButtonDefaults.IconSize),
                         tint = Color(0xFF263238) // Direct Use: Dark Blue-Grey (contrast on light blue)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Log Out", color = Color(0xFF263238)) // Direct Use: Dark Blue-Grey (contrast on light blue)
                }
            }
        }
    }
}

@Composable
fun ResumeSection(
    resumeUrl: String?,
    isLoading: Boolean,
    context: Context,
    pdfPickerLauncher: androidx.activity.result.ActivityResultLauncher<String>
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Section Title
        Text(
            text = "Manage Resume",
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFFECEFF1), // Direct Use: Blue Grey 50 (Light text on main dark bg)
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.padding(vertical = 32.dp),
                color = Color(0xFFFF9800) // Direct Use: Orange 500 (Loading indicator)
            )
        } else if (resumeUrl.isNullOrEmpty()) {
            // --- Upload Resume State ---
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { pdfPickerLauncher.launch("application/pdf") },
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                // Consistent card background with header
                colors = CardDefaults.cardColors(containerColor = Color(0xFF37474F)) // Direct Use: Blue Grey 700
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 32.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Filled.UploadFile, // Changed icon
                        contentDescription = "Upload Resume Icon",
                        tint = Color(0xFFFFA726), // Direct Use: Orange 400 (Accent icon)
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No Resume Uploaded",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFFECEFF1) // Direct Use: Blue Grey 50 (Light text)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Tap here to upload your PDF resume",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFB0BEC5), // Direct Use: Blue Grey 200 (Muted text)
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            // --- Resume Exists State ---
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                // Consistent card background
                colors = CardDefaults.cardColors(containerColor = Color(0xFF37474F)) // Direct Use: Blue Grey 700
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Info about attached resume
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Filled.PictureAsPdf,
                            contentDescription = "PDF Icon",
                            tint = Color(0xFF81D4FA), // Direct Use: Light Blue 300 (Accent icon)
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            "Resume Attached",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFECEFF1) // Direct Use: Blue Grey 50 (Light text)
                        )
                    }

                    // View Resume Button - Use Light Blue
                    Button(
                        onClick = {
                            // Original logic remains
                            try {
                                val intent = Intent(Intent.ACTION_VIEW).apply {
                                    setDataAndType(Uri.parse(resumeUrl), "application/pdf")
                                    flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_GRANT_READ_URI_PERMISSION
                                }
                                val chooserIntent = Intent.createChooser(intent, "Open PDF with")
                                startActivity(context, chooserIntent, null)
                            } catch (e: Exception) {
                                Log.e("ProfileScreen", "Error opening PDF viewer", e)
                                Toast.makeText(context, "Cannot open PDF. No suitable app found or invalid URL.", Toast.LENGTH_LONG).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4FC3F7) // Direct Use: Light Blue 400
                        )
                    ) {
                        Text("View PDF", color = Color(0xFF263238)) // Direct Use: Dark Blue-Grey (contrast on light blue)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Change Resume Button - Outlined style for less emphasis? Or just different color. Let's use Outlined.
            OutlinedButton( // Changed to OutlinedButton
                 onClick = { pdfPickerLauncher.launch("application/pdf") },
                 modifier = Modifier.fillMaxWidth(),
                 shape = MaterialTheme.shapes.medium,
                 border = BorderStroke(1.dp, Color(0xFF4FC3F7)), // Direct Use: Light Blue 400 border
                 colors = ButtonDefaults.outlinedButtonColors(
                     contentColor = Color(0xFF4FC3F7) // Direct Use: Light Blue 400 text/icon
                 )
            ) {
                Icon(
                    Icons.Default.ChangeCircle,
                    contentDescription = "Change Resume",
                     modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Change Resume") // Color from contentColor
            }
        }
    }
}

@Composable
fun UserInfoCard(userData: UserResponse?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        // Use a light background for readability of detailed info
        colors = CardDefaults.cardColors(containerColor = Color(0xFFECEFF1)) // Direct Use: Blue Grey 50
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Info and Experience side-by-side
             Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Info Column
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Info",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF9800), // Direct Use: Orange 500 (Title accent on light bg)
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    // InfoItem themed for light background
                    InfoItem(label = "Gender", value = userData?.gender)
                    InfoItem(label = "Location", value = userData?.address)
                    InfoItem(label = "Education", value = userData?.education)
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Experience Column
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Experience",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF9800), // Direct Use: Orange 500 (Title accent on light bg)
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    // InfoItem themed for light background
                    InfoItem(label = "Years", value = userData?.experience)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Skills Section
             Text(
                "Skills",
                style = MaterialTheme.typography.titleMedium,
                 fontWeight = FontWeight.Bold,
                color = Color(0xFFFF9800), // Direct Use: Orange 500 (Title accent on light bg)
                modifier = Modifier.padding(bottom = 8.dp)
            )
            val skillsText = userData?.skills?.takeIf { it.isNotEmpty() }?.joinToString(", ") ?: "-"
            Text(
                text = skillsText,
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF37474F) // Direct Use: Blue Grey 700 (Slightly softer dark text)
            )
        }
    }
}


// --- Main Profile Screen Composable ---

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun ProfileScreen(navController: NavHostController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val initialUserData = remember { PreferencesHelper.getUserData(context) }
    val userData = remember { mutableStateOf(initialUserData) }
    var isLoading by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }

    val userRole = userData.value?.role ?: "USER"
    val resumeUrl = userData.value?.resume

    // --- Callbacks (Logic remains unchanged) ---
    fun saveUserData(user: UserResponse) { /* ... original logic ... */
         PreferencesHelper.saveUserData(context, user)
         userData.value = user
         Log.d("ProfileScreen", "User data saved and UI state updated.")
    }

    fun handleLogout() { /* ... original logic ... */
         RetrofitClient.apiService.logout().enqueue(object : Callback<ResponseBody> {
             override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                PreferencesHelper.clearPreferences(App.context)
                Toast.makeText(App.context, "Logged out", Toast.LENGTH_SHORT).show()
                navController.navigate("login") { popUpTo(navController.graph.startDestinationId) { inclusive = true } }
                if (!response.isSuccessful) Log.e("ProfileScreen", "Logout API failed: ${response.code()}")
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                 Log.e("ProfileScreen", "Logout network error", t)
                 PreferencesHelper.clearPreferences(App.context)
                Toast.makeText(App.context, "Logged out (offline)", Toast.LENGTH_SHORT).show()
                 navController.navigate("login") { popUpTo(navController.graph.startDestinationId) { inclusive = true } }
            }
        })
    }

    val pdfPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? -> /* ... original logic ... */
             uri?.let { pdfUri ->
                coroutineScope.launch {
                    isLoading = true
                    try {
                        val cloudinaryResponse = FileUploadUtil.uploadFile(context, pdfUri, "resume")
                        Log.d("ProfileScreen", "Cloudinary Response: $cloudinaryResponse")
                        if (cloudinaryResponse != null && cloudinaryResponse.secure_url.isNotEmpty()) {
                            val updatedUser = userData.value?.copy(resume = cloudinaryResponse.secure_url)
                            if (updatedUser != null) {
                                saveUserData(updatedUser)
                                Toast.makeText(context, "Resume Uploaded Successfully", Toast.LENGTH_SHORT).show()
                            } else { Toast.makeText(context, "Error updating local user data", Toast.LENGTH_SHORT).show() }
                        } else { Toast.makeText(context, "Upload Failed: No response from server", Toast.LENGTH_SHORT).show() }
                    } catch (e: Exception) {
                        Log.e("ProfileScreen", "Error uploading PDF", e)
                        Toast.makeText(context, "Error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                    } finally { isLoading = false }
                }
            } ?: run { Log.d("ProfileScreen", "PDF picker cancelled.") }
        }
    )

    // --- UI Structure ---
    Scaffold(
        // Apply a softer, dark Blue-Grey background
        containerColor = Color(0xFF263238) // Direct Use: Blue Grey 800
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Profile Header Section (Rethemed)
            ProfileHeader(
                userData = userData.value,
                onEditClick = { showEditDialog = true },
                onLogoutClick = { handleLogout() }
            )

            // Resume section (Rethemed)
            if (userRole == "USER") {
                ResumeSection(
                    resumeUrl = resumeUrl,
                    isLoading = isLoading,
                    context = context,
                    pdfPickerLauncher = pdfPickerLauncher
                )
            } else { Spacer(modifier = Modifier.height(16.dp)) }

            // Info Card Section (Rethemed)
            UserInfoCard(userData = userData.value)

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    // --- Edit Profile Dialog (Logic remains unchanged, UI needs external theming) ---
    if (showEditDialog) {
        val userId = userData.value?._id ?: ""
        EditProfileDialog(
            user = userData.value,
            userId = userId,
            onDismiss = { showEditDialog = false },
            onSave = { updatedUserRequest -> /* ... original logic ... */
                 coroutineScope.launch {
                    var isDialogLoading by mutableStateOf(false) // Consider if dialog needs own loading state
                    isDialogLoading = true
                    try {
                        val response = RetrofitClient.apiService.updateUser(updatedUserRequest)
                        if (response.isSuccessful) {
                            val updatedUser = response.body()
                            if (updatedUser != null) {
                                saveUserData(updatedUser)
                                Toast.makeText(context, "Profile Updated Successfully", Toast.LENGTH_SHORT).show()
                                showEditDialog = false
                            } else { Toast.makeText(context, "Profile update succeeded but no data returned.", Toast.LENGTH_LONG).show() }
                        } else {
                            val errorMsg = response.errorBody()?.string() ?: "Unknown error (${response.code()})"
                            Log.e("ProfileScreen", "Failed to Update Profile: ${response.code()} - $errorMsg")
                            Toast.makeText(context, "Failed to Update Profile: $errorMsg", Toast.LENGTH_LONG).show()
                        }
                    } catch (e: Exception) {
                         Log.e("ProfileScreen", "Error updating profile", e)
                        Toast.makeText(context, "Error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                    } finally { isDialogLoading = false }
                }
            }
        )
         // IMPORTANT: The UI inside EditProfileDialog needs to be themed separately
         // using the same Color(0xFF...) approach if it's not already done.
         // (e.g., Dialog background, TextField colors, Save/Cancel button colors)
    }
}