package com.example.resume_parsing.viewmodel



import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.resume_parsing.network.Job
import com.example.resume_parsing.network.RetrofitClient
import com.example.resume_parsing.network.jobcountresponse

class JobListViewModel : ViewModel() {

    private val _jobs = MutableStateFlow<List<Job>>(emptyList())
    private val job_count=0;
    val jobs: StateFlow<List<Job>> = _jobs
    val jobcount :Int= job_count
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchJobs(hrId:String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getJobsPostedByHR(hrId)
                val response_job = RetrofitClient.apiService.JobOpenCounter()
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