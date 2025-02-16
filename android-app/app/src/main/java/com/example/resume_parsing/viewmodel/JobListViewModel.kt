package com.example.resume_parsing.viewmodel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.resume_parsing.network.Job
import com.example.resume_parsing.network.RetrofitClient

class JobListViewModel : ViewModel() {

    private val _jobs = MutableStateFlow<List<Job>>(emptyList())
    val jobs: StateFlow<List<Job>> = _jobs

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchJobs(hrId:String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getJobsPostedByHR(hrId)
                if (response.jobs.isNotEmpty()) {
                    _jobs.value = response.jobs
                } else {
                    _errorMessage.value = "Failed to fetch jobs: ${response.toString()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error fetching jobs: ${e.message}"
            }
        }
    }
}