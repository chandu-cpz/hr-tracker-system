
package com.example.resume_parsing.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.resume_parsing.network.ApplicationCountResponse
import com.example.resume_parsing.network.RetrofitClient

class DashboardViewModel : ViewModel() {

    private val _applicationCounts = MutableStateFlow<ApplicationCountResponse?>(null)
    val applicationCounts: StateFlow<ApplicationCountResponse?> = _applicationCounts

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchApplicationCounts(userId: String, userRole: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getApplicationCount(userId, userRole)
                if (response.isSuccessful) {
                    _applicationCounts.value = response.body()
                    Log.d("API ERROR","couts:"+response.body());

                } else {
                    _errorMessage.value = "Failed to fetch application counts: ${response.message()}"
                    Log.d("API ERROR","Failed to fetch application counts:"+response.message());
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error fetching application counts: ${e.message}"
                Log.d("API ERROR","Failed to fetch application counts:"+e.message);
            }
        }
    }
}