package com.example.resume_parsing.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resume_parsing.network.Applicant
import com.example.resume_parsing.network.RetrofitClient
import kotlinx.coroutines.launch

class EmployeeViewModel: ViewModel()  {

    val applicants = mutableStateListOf<Applicant>()
    var errorMessage: String? = null

    init {
        loadApplicants()
    }

    private fun loadApplicants() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getEmployee()
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        applicants.clear()
                        applicants.addAll(responseBody)
                    } else {
                        errorMessage = "Empty response body"
                    }
                } else {
                    errorMessage = "Request failed: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage = "Network error: ${e.message}"
            }
        }
    }
}

