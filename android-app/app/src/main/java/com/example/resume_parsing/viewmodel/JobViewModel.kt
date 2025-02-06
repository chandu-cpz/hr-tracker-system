package com.example.resume_parsing.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resume_parsing.network.RetrofitClient
import com.example.resume_parsing.network.Job
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

class JobViewModel : ViewModel() {
    private val _jobs = mutableStateOf<List<Job>>(emptyList())
    val jobs: State<List<Job>> = _jobs

    init {
        fetchJobs()
    }

    private fun fetchJobs() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getAllJobs()
                _jobs.value = response.jobs
            } catch (e: Exception) {
                println("Error fetching jobs: ${e.message}")
            }
        }
    }
}
