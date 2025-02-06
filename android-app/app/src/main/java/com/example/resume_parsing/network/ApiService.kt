package com.example.resume_parsing.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

data class UserSignUpRequest(
    val fullName: String,
    val email: String,
    val password: String,
    val gender: String = "male",   // Default value if not passed
    val role: String = "User",     // Default value if not passed
    val company: String? = null,
    val companyImage: String? = null
)
data class Job(
    val _id: String,
    val jobTitle: String,
    val jobDescription: String,
    val companyName: String,
    val responsibilities: String,
    val qualifications: String,
    val location: String,
    val jobtype: String,
    val noOfPosts: Int,
    val salary: Int,
    val isOpen: Boolean,
    val skills: List<String>,
    val experience: String?,
    val postedBy: PostedBy?,
    val appliedBy: List<String>?,
    val createdAt: String?,
    val updatedAt: String?
)

data class PostedBy(
    val companyImage: String?
)

data class JobResponse(val jobs: List<Job>)
data class LoginRequest(val email: String, val password: String)
data class UserResponse(val email: String, val fullName: String, val error: String? = null)
interface ApiService {
    @POST("/api/signup")  // Replace with your actual API endpoint
    suspend fun signUp(@Body userSignUpRequest: UserSignUpRequest): Response<Unit>
    @POST("/api/login")  // Replace with your server's login route
    fun loginUser(@Body loginRequest: LoginRequest): Call<UserResponse>
    @GET("api/login/")
    fun logout(): Call<ResponseBody>
    @GET("api/login/auto")
    suspend fun AutoLogin(): Response<UserResponse> // The token will automatically be sent in cookies
    @GET("api/jobs/all")  // Replace with your actual endpoint
    suspend fun getAllJobs(): JobResponse
}
