package com.example.resume_parsing.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resume_parsing.network.Application
import com.example.resume_parsing.network.ApplicationIdRequest
import com.example.resume_parsing.network.RetrofitClient
import kotlinx.coroutines.launch


class ApplicationDetailsViewModel : ViewModel() {
    private val _application = mutableStateOf<Application?>(null)
    val application: androidx.compose.runtime.State<Application?> = _application

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: androidx.compose.runtime.State<String?> = _errorMessage

    private val _accepted = mutableStateOf(false)
    val accepted: androidx.compose.runtime.State<Boolean> = _accepted

    private val _rejected = mutableStateOf(true)
    val rejected: androidx.compose.runtime.State<Boolean> = _rejected


    fun loadApplicationDetails(applicationId: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getApplicationDetails(ApplicationIdRequest(applicationId)) // Pass as request body
                if (response.isSuccessful) {
                    _application.value = response.body()
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = "Failed to load application details: ${response.code()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Network error: ${e.message}"
            }
        }
    }

    fun acceptApplication(applicationId: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.acceptApplication(ApplicationIdRequest(applicationId))

                if (response.isSuccessful) {
                    _accepted.value = true
                    _rejected.value = false // Assuming accepting means it can't be rejected anymore

                    _errorMessage.value = null

                } else {
                    _errorMessage.value = "Failed to accept application: ${response.code()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Network error: ${e.message}"
            }
        }
    }

    fun rejectApplication(applicationId: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.rejectApplication(ApplicationIdRequest(applicationId))

                if (response.isSuccessful) {
                    _rejected.value = true
                    _accepted.value = false

                    _errorMessage.value = null
                } else {
                    _errorMessage.value = "Failed to reject application: ${response.code()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Network error: ${e.message}"
            }
        }
    }
}