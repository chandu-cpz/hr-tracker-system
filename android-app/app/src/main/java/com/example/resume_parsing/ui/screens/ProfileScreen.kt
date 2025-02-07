package com.example.resume_parsing.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.resume_parsing.App
import com.example.resume_parsing.network.RetrofitClient
import com.example.resume_parsing.utils.PreferencesHelper
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ProfileScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("User Profile", fontSize = 24.sp, color = Color.Black)

        Button(
            onClick = {
                RetrofitClient.apiService.logout().enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            PreferencesHelper.clearToken(App.context)
                            Toast.makeText(App.context, "Logged out successfully", Toast.LENGTH_SHORT).show()
                            navController.navigate("login") {
                                popUpTo("login") { inclusive = true } // Clears backstack
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
            modifier = Modifier.padding(16.dp),
            colors = ButtonDefaults.buttonColors(Color.Red)
        ) {
            Text("Log Out", color = Color.White)
        }
    }
}
