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
import androidx.compose.material.icons.filled.* // Import common icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color // Keep original explicit colors for now
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
import com.example.resume_parsing.utils.FileUploadUtil // Ensure this path is correct
import com.example.resume_parsing.utils.FileUtils
import com.example.resume_parsing.utils.PreferencesHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// --- Helper Composables (Slightly enhanced) ---

@Composable
fun InfoItem(label: String, value: String?) { // Make value nullable for safety
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black // Using original color for now
            // color = MaterialTheme.colorScheme.onSurfaceVariant // Theme alternative
        )
        Text(
            text = value?.takeIf { it.isNotEmpty() } ?: "-", // Use takeIf for cleaner check
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black // Using original color for now
           // color = MaterialTheme.colorScheme.onSurfaceVariant // Theme alternative
        )
    }
}

@Composable
fun ProfileHeader(
    userData: UserResponse?,
    onEditClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    // Use original background color
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFE5C2)), // Original background color
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            // Profile Image
            Image(
                painter = rememberAsyncImagePainter(
                    model = userData?.profileImage?.takeIf { it.isNotEmpty() }
                        ?: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS2IYhSn8Y9S9_HF3tVaYOepJBcrYcd809pBA&s" // Placeholder
                ),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color(0xFFE65100), CircleShape), // Use original accent color for border
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Name
            Text(
                text = userData?.fullName ?: "Unknown User",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE65100) // Original accent color
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Email with Icon
            Row(verticalAlignment = Alignment.CenterVertically){
                Icon(
                    Icons.Default.Email,
                    contentDescription = "Email Icon",
                    modifier = Modifier.size(16.dp),
                    tint = Color.Black.copy(alpha = 0.7f) // Slightly muted black
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = userData?.email ?: "No Email Provided",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black.copy(alpha = 0.8f)
                )
            }


            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
            ) {
                // Edit Profile Button
                Button(
                    onClick = onEditClick,
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF7043) // Original button color
                    )
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Edit Profile",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Edit Profile", color = Color.White)
                }

                // Logout Button
                Button( // Keep as filled Button as per original, but use Red
                    onClick = onLogoutClick,
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red // Original button color
                    )
                ) {
                     Icon(
                        Icons.Default.Logout,
                        contentDescription = "Log Out",
                         modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Log Out", color = Color.White)
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
    pdfPickerLauncher: androidx.activity.result.ActivityResultLauncher<String> // Explicit type
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Manage Resume",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White, // Text on dark background
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.padding(vertical = 32.dp),
                color = Color(0xFFFF7043) // Original accent color
            )
        } else if (resumeUrl.isNullOrEmpty()) {
            // --- Upload Resume State ---
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { pdfPickerLauncher.launch("application/pdf") },
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                 // Use a lighter shade for contrast on dark background
                colors = CardDefaults.cardColors(containerColor = Color(0xFF455A64))
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 32.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Filled.AttachFile,
                        contentDescription = "Upload Resume Icon",
                        tint = Color(0xFFFFE5C2), // Lighter color for icon
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No Resume Uploaded",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White // Text on card background
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Tap here to upload your PDF resume",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.8f), // Slightly muted white
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
                 // Use a lighter shade for contrast on dark background
                colors = CardDefaults.cardColors(containerColor = Color(0xFF455A64))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween // Space out icon/text and button
                ) {
                    // Info about attached resume
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Filled.PictureAsPdf,
                            contentDescription = "PDF Icon",
                            tint = Color(0xFFFFE5C2), // Lighter color for icon
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            "Resume Attached",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium,
                            color = Color.White // Text on card background
                        )
                    }

                    // View Resume Button
                    Button(
                        onClick = {
                            try {
                                val intent = Intent(Intent.ACTION_VIEW).apply {
                                    setDataAndType(Uri.parse(resumeUrl), "application/pdf")
                                    // Flags might help ensure viewer apps can access the URI
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
                            containerColor = Color(0xFFFF7043) // Original button color
                        )
                    ) {
                        Text("View PDF", color = Color.White) // Clearer button text
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Change Resume Button
            Button( // Keep as filled Button as per original
                 onClick = { pdfPickerLauncher.launch("application/pdf") },
                 modifier = Modifier.fillMaxWidth(), // Make it full width for easy tapping
                 shape = MaterialTheme.shapes.medium,
                 colors = ButtonDefaults.buttonColors(
                     containerColor = Color(0xFFFF7043).copy(alpha = 0.8f) // Slightly less prominent? Or keep same
                 )
            ) {
                Icon(
                    Icons.Default.ChangeCircle, // Changed Icon
                    contentDescription = "Change Resume",
                     modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Change Resume", color = Color.White)
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
        shape = MaterialTheme.shapes.large, // Nicer rounded corners
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE5C2)) // Original card color
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Use Row for side-by-side Info and Experience as in original
             Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Info Column
                Column(modifier = Modifier.weight(1f)) { // Use weight for flexible width
                    Text(
                        "Info",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE65100), // Original title color
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    InfoItem(label = "Gender", value = userData?.gender)
                    InfoItem(label = "Location", value = userData?.address)
                    InfoItem(label = "Education", value = userData?.education)
                }

                Spacer(modifier = Modifier.width(16.dp)) // Add space between columns

                // Experience Column
                Column(modifier = Modifier.weight(1f)) { // Use weight for flexible width
                    Text(
                        "Experience",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE65100), // Original title color
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    InfoItem(label = "Years", value = userData?.experience)
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            // Skills Section Title
             Text(
                "Skills",
                style = MaterialTheme.typography.titleMedium,
                 fontWeight = FontWeight.Bold,
                color = Color(0xFFE65100), // Original title color
                modifier = Modifier.padding(bottom = 8.dp)
            )
            // Skills List (or placeholder)
            val skillsText = userData?.skills
                ?.takeIf { it.isNotEmpty() }
                ?.joinToString(", ")
                ?: "-"
            Text(
                text = skillsText,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black // Original text color on card
            )
        }
    }
}


// --- Main Profile Screen Composable ---

@RequiresApi(Build.VERSION_CODES.Q) // Still potentially needed for FileUtils.uriToFile
@Composable
fun ProfileScreen(navController: NavHostController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // State for user data, loading, and dialog visibility
    val initialUserData = remember { PreferencesHelper.getUserData(context) }
    val userData = remember { mutableStateOf(initialUserData) }
    var isLoading by remember { mutableStateOf(false) } // Loading state specifically for resume upload/change
    var showEditDialog by remember { mutableStateOf(false) }

    // Derived state (read-only)
    val userRole = userData.value?.role ?: "USER"
    val resumeUrl = userData.value?.resume

    // --- Callbacks ---
    // Function to save UserData to SharedPreferences AND update local state
    fun saveUserData(user: UserResponse) {
        PreferencesHelper.saveUserData(context, user)
        userData.value = user // Update the MutableState to trigger recomposition
        Log.d("ProfileScreen", "User data saved to SharedPreferences and UI state updated.")
    }

    fun handleLogout() {
        // Keep original logout logic
        RetrofitClient.apiService.logout().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                // Clear prefs and navigate regardless of server response for better UX
                PreferencesHelper.clearPreferences(App.context)
                Toast.makeText(App.context, "Logged out", Toast.LENGTH_SHORT).show()
                navController.navigate("login") {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true } // Clear back stack
                }
                if (!response.isSuccessful) {
                    Log.e("ProfileScreen", "Logout API call failed: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("ProfileScreen", "Logout network error", t)
                // Clear prefs and navigate regardless of network error
                PreferencesHelper.clearPreferences(App.context)
                Toast.makeText(App.context, "Logged out (offline)", Toast.LENGTH_SHORT).show()
                 navController.navigate("login") {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                }
            }
        })
    }

    // Activity Result Launcher for PDF picking
    val pdfPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let { pdfUri ->
                coroutineScope.launch {
                    isLoading = true // Start loading indicator for upload process
                    try {
                        // Use the FileUploadUtil as in the original code
                        val cloudinaryResponse = FileUploadUtil.uploadFile(
                            context,
                            pdfUri,
                            "resume" // Assuming "resume" is the desired identifier/folder
                        )
                        Log.d("ProfileScreen", "Cloudinary Response: $cloudinaryResponse")

                        if (cloudinaryResponse != null && cloudinaryResponse.secure_url.isNotEmpty()) {
                            // --- IMPORTANT: Update local data and SharedPreferences ONLY ---
                            val updatedUser = userData.value?.copy(resume = cloudinaryResponse.secure_url)
                            if (updatedUser != null) {
                                saveUserData(updatedUser) // This now saves to prefs and updates state
                                Toast.makeText(context, "Resume Uploaded Successfully", Toast.LENGTH_SHORT).show()
                            } else {
                                // Should not happen if userData.value was not null initially
                                Toast.makeText(context, "Error updating local user data", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "Upload Failed: No response from server", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.e("ProfileScreen", "Error uploading PDF", e)
                        // Use localizedMessage for potentially more user-friendly error
                        Toast.makeText(context, "Error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                    } finally {
                        isLoading = false // Stop loading indicator
                    }
                }
            } ?: run {
                 Log.d("ProfileScreen", "PDF picker cancelled.")
            }
        }
    )

    // --- UI Structure ---
    Scaffold(
        // Use the original dark background color
        containerColor = Color(0xFF2C3E50)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Apply padding from Scaffold
                .verticalScroll(rememberScrollState()) // Make content scrollable
        ) {
            // Profile Header Section
            ProfileHeader(
                userData = userData.value,
                onEditClick = { showEditDialog = true },
                onLogoutClick = { handleLogout() }
            )

            // Conditionally render Resume section for USER role
            if (userRole == "USER") {
                ResumeSection(
                    resumeUrl = resumeUrl,
                    isLoading = isLoading, // Pass loading state
                    context = context,
                    pdfPickerLauncher = pdfPickerLauncher
                )
            } else {
                 Spacer(modifier = Modifier.height(16.dp)) // Add space if resume section not shown
            }

            // Info Card Section
            UserInfoCard(userData = userData.value)

            // Add some padding at the bottom
             Spacer(modifier = Modifier.height(16.dp))
        }
    }

    // --- Edit Profile Dialog ---
    if (showEditDialog) {
        val userId = userData.value?._id ?: ""
        // Ensure EditProfileDialog exists and is correctly defined elsewhere
        // Use the original logic for the dialog save action
        EditProfileDialog(
            user = userData.value,
            userId = userId, // Pass userId as originally intended if needed by dialog
            onDismiss = { showEditDialog = false },
            onSave = { updatedUserRequest -> // Receive the updated user data request
                // Keep original API call logic within the dialog save action
                coroutineScope.launch {
                    var isDialogLoading by mutableStateOf(false) // Dialog specific loading maybe? Or reuse global isLoading?
                    isDialogLoading = true
                    try {
                        // Assuming updateUser takes the request object
                        val response = RetrofitClient.apiService.updateUser(updatedUserRequest)
                        if (response.isSuccessful) {
                            val updatedUser = response.body()
                            if (updatedUser != null) {
                                saveUserData(updatedUser) // Save to SharedPreferences & update state
                                Toast.makeText(context, "Profile Updated Successfully", Toast.LENGTH_SHORT).show()
                                showEditDialog = false // Close dialog on success
                            } else {
                                Toast.makeText(context, "Profile update succeeded but no data returned.", Toast.LENGTH_LONG).show()
                             }
                        } else {
                            val errorMsg = response.errorBody()?.string() ?: "Unknown error"
                            Log.e("ProfileScreen", "Failed to Update Profile: ${response.code()} - $errorMsg")
                            Toast.makeText(context, "Failed to Update Profile: $errorMsg", Toast.LENGTH_LONG).show()
                        }
                    } catch (e: Exception) {
                        Log.e("ProfileScreen", "Error updating profile", e)
                        Toast.makeText(context, "Error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                    } finally {
                         isDialogLoading = false
                        // Decide if dialog should close on failure
                        // showEditDialog = false
                    }
                }
            }
        )
    }
}
