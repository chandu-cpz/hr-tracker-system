package com.example.resume_parsing.ui.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.resume_parsing.App
import com.example.resume_parsing.network.RetrofitClient
import com.example.resume_parsing.utils.PreferencesHelper
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun ProfileScreen(navController: NavHostController) {
    val context = App.context
    val userData = remember { mutableStateOf(PreferencesHelper.getUserData(context)) }
    var showEditDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color(0xFFFFE5C2)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
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

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.White),
            contentAlignment = Alignment.TopCenter
        ) {
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
    }

    if (showEditDialog) {
        EditProfileDialog(
            user = userData.value,
            onDismiss = { showEditDialog = false },
            onSave = { updatedUser ->
                userData.value = updatedUser
                PreferencesHelper.saveUserData(context, updatedUser)
                showEditDialog = false
            }
        )
    }
}

@Composable
fun InfoItem(label: String, value: String) {
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Text("$label: ", fontSize = 16.sp, color = Color.Black)
        Text(value.ifEmpty { "-" }, fontSize = 16.sp, color = Color.Black)
    }
}
