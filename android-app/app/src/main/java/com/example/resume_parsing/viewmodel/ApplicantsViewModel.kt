//import androidx.compose.runtime.mutableStateListOf
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.resume_parsing.network.Applicant
//import com.example.resume_parsing.network.RetrofitClient
//import kotlinx.coroutines.launch
//
//class ApplicantsViewModel : ViewModel() {
//
//    val applicants = mutableStateListOf<Applicant>() // Use a MutableStateList for Compose to observe changes
//    var errorMessage: String? = null  // For storing potential error messages
//
//    init {
//        loadApplicants() // Load applicants when the ViewModel is created
//    }
//
//
//    private fun loadApplicants() {
//        viewModelScope.launch {
//            try {
//                val response = RetrofitClient.apiService.getApplicants()
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//                    if (responseBody != null) {
//                        applicants.clear()
//                        applicants.addAll(responseBody)
//                    } else {
//                        errorMessage = "Empty response body"
//                    }
//                } else {
//                    errorMessage = "Request failed: ${response.code()}"
//                }
//            } catch (e: Exception) {
//                errorMessage = "Network error: ${e.message}"
//            }
//        }
//    }
//}
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.resume_parsing.network.Applicant
import com.example.resume_parsing.network.Application
import com.example.resume_parsing.network.ApplicationIdRequest
import com.example.resume_parsing.network.RetrofitClient

class ApplicantsViewModel : ViewModel() {

    val applicants = mutableStateListOf<Applicant>()
    var errorMessage: String? = null

    init {
        loadApplicants()
    }

    private fun loadApplicants() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getApplicants()
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
