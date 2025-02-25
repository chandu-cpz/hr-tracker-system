package com.example.resume_parsing.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resume_parsing.network.Job

import com.example.resume_parsing.network.RetrofitClient
import kotlinx.coroutines.launch

class JobViewModel : ViewModel() {

    // Regular Job List State
    private val _jobs = mutableStateOf<List<Job>>(emptyList())
    val jobs: State<List<Job>> = _jobs

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _isError = mutableStateOf(false)
    val isError: State<Boolean> = _isError

    private val _page = mutableStateOf(1) // Start at page 1
    val page: State<Int> = _page

    private val _totalPages = mutableStateOf(1)
    val totalPages: State<Int> = _totalPages

    // Recommended Job List State
    private val _recommendedJobs = mutableStateOf<List<Job>>(emptyList())
    val recommendedJobs: State<List<Job>> = _recommendedJobs

    private val _recommendationsLoading = mutableStateOf(false)
    val recommendationsLoading: State<Boolean> = _recommendationsLoading

    private val _recommendationsError = mutableStateOf(false)
    val recommendationsError: State<Boolean> = _recommendationsError

    // Query parameters
    private val _jobTitle = mutableStateOf<String?>(null)
    val jobTitle: State<String?> = _jobTitle

    private val _location = mutableStateOf<String?>(null)
    val location: State<String?> = _location

    private val _experience = mutableStateOf<String?>(null)
    val experience: State<String?> = _experience

    private val _jobType = mutableStateOf<String?>(null)
    val jobType: State<String?> = _jobType

    private val _minSalary = mutableStateOf<String?>(null)
    val minSalary: State<String?> = _minSalary

    private val _maxSalary = mutableStateOf<String?>(null)
    val maxSalary: State<String?> = _maxSalary

    private val _sortField = mutableStateOf<String?>("updatedAt")
    val sortField: State<String?> = _sortField

    private val _sortOrder = mutableStateOf<String?>("desc")
    val sortOrder: State<String?> = _sortOrder


    init {
        loadJobs()
    }


    fun setJobTitle(title: String?) {
        _jobTitle.value = title
        resetAndLoadJobs()
    }

    fun setLocation(location: String?) {
        _location.value = location
        resetAndLoadJobs()
    }

    fun setExperience(exp: String?) {
        _experience.value = exp
        resetAndLoadJobs()
    }

    fun setJobType(type: String?) {
        _jobType.value = type
        resetAndLoadJobs()
    }

    fun setMinSalary(min: String?) {
        _minSalary.value = min
        resetAndLoadJobs()
    }

    fun setMaxSalary(max: String?) {
        _maxSalary.value = max
        resetAndLoadJobs()
    }

    fun setSortField(sort: String?) {
        _sortField.value = sort
        resetAndLoadJobs()
    }

    fun setSortOrder(order: String?) {
        _sortOrder.value = order
        resetAndLoadJobs()
    }

    fun loadMoreJobs() {
        if (_page.value < _totalPages.value) {
            _page.value++
            loadJobs()
        }
    }

    private fun resetAndLoadJobs() {
        _jobs.value = emptyList()
        _page.value = 1
        loadJobs()
    }


    private fun loadJobs() {
        viewModelScope.launch {
            _isLoading.value = true
            _isError.value = false // Reset error state

            try {
                val response = RetrofitClient.apiService.getAllJobs(
                    page = _page.value,
                    jobTitle = _jobTitle.value,
                    location = _location.value,
                    experience = _experience.value,
                    jobType = _jobType.value,
                    minSalary = _minSalary.value,
                    maxSalary = _maxSalary.value,
                    sort = _sortField.value,
                    sortOrder = _sortOrder.value
                )

                _jobs.value = _jobs.value + response.jobs // Append new jobs
                _totalPages.value = response.totalPages

            } catch (e: Exception) {
                println("Error fetching jobs: ${e.message}")
                _isError.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadRecommendedJobs() {
        viewModelScope.launch {
            _recommendationsLoading.value = true
            _recommendationsError.value = false

            try {
                val response = RetrofitClient.apiService.getRecommendedJobs()
                _recommendedJobs.value = response.recommendedJobs

            } catch (e: Exception) {
                println("Error fetching recommended jobs: ${e.message}")
                _recommendationsError.value = true
            } finally {
                _recommendationsLoading.value = false
            }
        }
    }
}